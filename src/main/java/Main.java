import com.my.CoachEntity;
import com.my.TicketEntity;
import com.my.TrainEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Scanner;


public class Main {

    private static SessionFactory ourSessionFactory;

    static {
        try { // Create the SessionFactory from hibernate.cfg.xml
            ourSessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
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

//            ReadBookOfPerson(session);

//            ReadCityFilter(session);

//            ReadCityTable(session);
//            insertCity(session);
//            ReadCityTable(session);

//            insertPerson(session);

//            ReadCityFilter(session);

//            AddBookForPerson(session);
//            ReadAllTable(session);

//            ReadCityTable(session);
//            updateCity(session);
//            ReadAllTable(session);

//            ReadBookOfPerson(session);
//            AddPairPersonBookWithProcedure(session);
//            ReadBookOfPerson(session);

            System.out.println("Finish work!");
        } finally {
            session.close();
            System.exit(0);
        }
    }

    private static void ReadAllTable(Session session) {

//region read coach
        Query query = session.createQuery("from " + "CoachEntity");
        System.out.format("\ntable coach --------------------\n");
        System.out.format("%6d %-12s %-12s %-10s %s\n", "id_coach", "number_place", "type_coach", "number_coach", "train_id");
        for (Object object : query.list()) {
            CoachEntity coach = (CoachEntity) object;
            System.out.format("%6d %-12s %-12s %-10s %s\n", coach.getIdCoach(),
                    coach.getNumberPlace(), coach.getTypeCoach(), coach.getTrainByTrain().getNumberTrain(), coach.getNumberCoach());
        }
        //endregion

//region read ticket
        query = session.createQuery("from " + "TicketEntity");
        System.out.format("\nTable Book --------------------\n");
        System.out.format("%3s %-18s %-18s %-18s %s\n", "id_ticket", "date_departuere", "place_departuere", "place_arrival", "price_ticket");
        for (Object object : query.list()) {
            TicketEntity ticket = (TicketEntity) object;
            System.out.format("%3d %-18s %-18s %-18s %s\n", ticket.getIdTicket(), ticket.getDateDepartuere(), ticket.getPlaceDepartuere(), ticket.getPlaceArrival(), ticket.getPriceTicket());
        }
        //endregion

//region read train
        query = session.createQuery("from " + "TrainEntity");
        System.out.format("\nTable City --------------------\n");
        for (Object obj : query.list()) {
            TrainEntity train = (TrainEntity) obj;
            System.out.format("%3d %s\n", train.getNumberTrain());
        }
        //endregion

    }

    private static void ReadCityFilter(Session session) {

        Scanner input = new Scanner(System.in);
        System.out.println("Input name city for Person: ");
        String city_in = input.next();

        TrainEntity trainEntity = (TrainEntity) session.load(TrainEntity.class, city_in);
        if (trainEntity != null) {
            System.out.format("\n%s: %s\n", city_in, "Surname");
            for (CoachEntity obj : trainEntity.getCoachByTrain())
                System.out.format("    %s\n", obj.getNumberPlace());
        } else System.out.println("invalid name of city");
    }

    private static void ReadBookOfPerson(Session session) {
        Query query = session.createQuery("from " + "CoachEntity");
        System.out.format("\nTable Person --------------------\n");
        System.out.format("%3s %-12s %-12s \n", "ID", "Surname", "Name");
        for (Object obj : query.list()) {
            CoachEntity person = (CoachEntity) obj;
            System.out.format("%3s %-12s %-12s->\n", person.getIdCoach(), person.getNumberPlace(), person.getTypeCoach());
            for (TicketEntity booky : person.getTickets()) {
                System.out.format("\t\t%s // %s\n", booky.getDateDepartuere(), booky.getPlaceDepartuere());
            }
        }
    }

    private static void ReadCityTable(Session session) {

        Query query = session.createQuery("from " + "TrainEntity");
        System.out.format("\nTable City --------------------\n");
        for (Object obj : query.list()) {
            TrainEntity city = (TrainEntity) obj;
            System.out.format("%s\n", city.getNumberTrain());
        }
    }

    private static void insertCity(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name city: ");
        String newcity = input.next();

        session.beginTransaction();
        TrainEntity trainEntity = new TrainEntity(newcity);
        session.save(trainEntity);
        session.getTransaction().commit();

        System.out.println("end insert city");
    }

    private static void insertPerson(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input new Person Surname: ");
        int surname_new = Integer.parseInt(input.next());
        System.out.println("Input new Person Surname: ");
        String name_new = input.next();
        System.out.println("Input the City for Person: ");
        String city = input.next();
        System.out.println("Input new Person Email: ");
        String email = input.next();

        session.beginTransaction();
        CoachEntity coachEntity = new CoachEntity(surname_new, name_new, city, email);
        session.save(coachEntity);
        session.getTransaction().commit();
        System.out.println("end insert person");
    }

    private static void updateCity(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput a name city: ");
        String numberTrain = input.next();
        System.out.println("Input new name city: ");
        String newNumberTrain = input.next();

        TrainEntity trainEntity = (TrainEntity) session.load(TrainEntity.class, numberTrain);
        if (trainEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("update TrainEntity set numberTrain=:code1  where numberTrain = :code2");
            query.setParameter("code1", newNumberTrain);
            query.setParameter("code2", numberTrain);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("end update city: " + result);
        } else System.out.println("There is no the city");
    }

    private static void AddBookForPerson(Session session) {
        System.out.println("Give a book to person--------------");
        Scanner input = new Scanner(System.in);
        System.out.println("Choose Person Surname:");
        String surname_in = input.next();
        System.out.println("Choose Name Book:");
        String book_in = input.next();

        Query query = session.createQuery("from " + "CoachEntity where numberPlace = :code");
        query.setParameter("code", surname_in);

        if (!query.list().isEmpty()) {
            //Give this person entity from query
            CoachEntity coachEntity = (CoachEntity) query.list().get(0);
            //search the book entity  from query
            query = session.createQuery("from " + "TicketEntity where dateDepartuere = :code");
            query.setParameter("code", book_in);
            if (!query.list().isEmpty()) {
                //Give this book entity from query
                TicketEntity ticketEntity = (TicketEntity) query.list().get(0);
                session.beginTransaction();
                coachEntity.addTicketEntity(ticketEntity);
                session.save(coachEntity);
                session.getTransaction().commit();
                System.out.println("end insert boor for person");
            } else {
                System.out.println("There is no the book");
            }
        } else {
            System.out.println("There is no this person");
        }

    }

    private static void AddPairPersonBookWithProcedure(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput Surname for Person: ");
        String surname = input.next();
        System.out.println("Input NameBook for Book: ");
        String book = input.next();

        //to JPA 2.0
//        Query query = session.createSQLQuery(
//                "CALL InsertPersonBook(:Person, :Book)")
//                .setParameter("Person", surname)
//                .setParameter("Book", book);
//        System.out.println(query.list().get(0));

        //from JPA 2.1
        StoredProcedureQuery query = session
                .createStoredProcedureQuery("InsertPersonBook")
                .registerStoredProcedureParameter("SurmanePersonIn", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("BookNameIN", String.class, ParameterMode.IN)
                .setParameter("SurmanePersonIn", surname)
                .setParameter("BookNameIN", book);
        query.execute();
        String str = (String) query.getResultList().get(0);
        System.out.println(str);

        if (str.equals("OK")) {
            Query query2 = session.createQuery("from " + "CoachEntity");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
            query2 = session.createQuery("from " + "TicketEntity ");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
        }
    }

}