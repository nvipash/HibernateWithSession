import com.my.BookEntity;
import com.my.CityEntity;
import com.my.PersonEntity;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.transaction.Transactional;
import java.util.Scanner;


public class Main {

    private static SessionFactory ourSessionFactory;
    static {
        try { // Create the SessionFactory from hibernate.cfg.xml
            ourSessionFactory =  new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) { throw new ExceptionInInitializerError(ex); }
    }
    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession(); //return opened session
    }
    //---------------------------------------------------------------------------
    public static void main(final String[] args) throws Exception {
        // get opened session
        Session session = getSession();
        try {

            ReadAllTable(session);

            //ReadCityFilter(session);

            insertCity(session);

            //insertPerson(session);

            //ReadCityFilter(session);

        } finally { session.close();  }
    }

    private static void ReadAllTable(Session session){

//region Read Person
        Query query = session.createQuery("from " + "PersonEntity");
        System.out.format("\nTable Person --------------------\n");
        System.out.format("%3s %-12s %-12s %-10s %s\n", "ID", "Surname", "Name", "City", "Email");
        for (Object obj : query.list()) {
            PersonEntity person = (PersonEntity) obj;
            System.out.format("%3d %-12s %-12s %-10s %s\n", person.getIdPerson(),
                    person.getSurname(), person.getName(), person.getCityByCity().getCity(), person.getEmail());
        }
        //endregion

//region Read Book
        query = session.createQuery("from " + "BookEntity");
        System.out.format("\nTable Book --------------------\n");
        System.out.format("%3s %-18s %-18s %s\n", "ID", "BookName", "Author", "Amount");
        for (Object obj : query.list()) {
            BookEntity book = (BookEntity) obj;
            System.out.format("%3d %-18s %-18s %s\n", book.getIdBook(), book.getBookName(), book.getAuthor(), book.getAmount());
        }
        //endregion

//region Read City
        query = session.createQuery("from " + "CityEntity");
        System.out.format("\nTable City --------------------\n");
        for (Object obj : query.list()) {
            CityEntity city = (CityEntity) obj;
            System.out.format("%s\n", city.getCity());
        }
        //endregion

//region Read Books of Person
        query = session.createQuery("from " + "PersonEntity");
        System.out.format("\nTable Person --------------------\n");
        System.out.format("%3s %-12s %-12s \n","ID", "Surname", "Name");
        for (Object obj : query.list()) {
            PersonEntity person = (PersonEntity) obj;
            System.out.format("%3s %-12s %-12s->\n", person.getIdPerson(), person.getSurname(), person.getName());
            for (BookEntity booky : person.getBooks()) {
                System.out.format("\t\t%s // %s\n", booky.getBookName(),  booky.getAuthor());
            }
        }
        //endregion

    }

    private static void ReadCityFilter(Session session){

        Scanner input = new Scanner(System.in);
        System.out.println("Input name city for Person: ");
        String city_in = input.next();

        Query query = session.createQuery("from " + "CityEntity where city=:code");
        query.setParameter("code", city_in);
        if(!query.list().isEmpty()) {
            System.out.format("\n%s: %s\n", city_in, "Surname");
            CityEntity city = (CityEntity) query.list().get(0);

            for (PersonEntity obj : city.getPeopleByCity())
                System.out.format("    %s\n", obj.getSurname());
        }
        else System.out.println("invalid name of city");
    }

    private static void insertCity(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name city: ");
        String newcity = input.next();
        session.beginTransaction();
        CityEntity cityEntity=new CityEntity(newcity);
        session.save(cityEntity);
        session.getTransaction().commit();
        System.out.println("end insert city");
    }

    private static void insertPerson(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("Input new Person Surname: ");
        String surname_new = input.next();
        System.out.println("Input new Person Surname: ");
        String name_new = input.next();
        System.out.println("Input the City for Person: ");
        String city = input.next();
        System.out.println("Input new Person Email: ");
        String email = input.next();

        session.beginTransaction();
        PersonEntity personEntity=new PersonEntity(surname_new,name_new,city,email);
        session.save(personEntity);
        session.getTransaction().commit();
        System.out.println("end insert person");
    }

    private static void AddBookForPerson(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("Choose Person Surname: ");
        String surname = input.next();
        System.out.println("Input Name Book : ");
        String book = input.next();

        session.beginTransaction();

        Query query = session.createQuery("from " + "PersonEntity"+"where Surname=");
        System.out.format("\nTable Person --------------------\n");
        System.out.format("%3s %-12s %-12s %-10s %s\n", "ID", "Surname", "Name", "City", "Email");
        for (Object obj : query.list()) {
            PersonEntity person = (PersonEntity) obj;
            System.out.format("%3d %-12s %-12s %-10s %s\n", person.getIdPerson(),
                    person.getSurname(), person.getName(), person.getCityByCity().getCity(), person.getEmail());
        }

//        PersonEntity personEntity=new PersonEntity(surname,name_new,city,email);
//        session.save(personEntity);
        session.getTransaction().commit();
        System.out.println("end insert boor for person");
    }


}