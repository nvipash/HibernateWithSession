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
//
//            ReadCoachByTicket(session);

//            ReadTrainFilter(session);

//            ReadTrainTable(session);
//            insertTrain(session);
//            ReadTrainTable(session);

//            ReadCityFilter(session);

//            AddTicketForCoach(session);

//            ReadCityTable(session);
//            updateCoach(session);
//            ReadAllTable(session);

            ReadCoachByTicket(session);
//            AddPairTicketCoachWithProcedure(session);

        } finally {
            session.close();
            System.exit(0);
        }
    }

    private static void ReadAllTable(Session session) {

//region read coach
        Query query = session.createQuery("from " + "CoachEntity");
        System.out.format("\ntable coach ------------------------------------------------\n");
        System.out.format("%-10s %-18s %-12s %-12s %s\n", "id_coach", "number_place", "type_coach", "number_coach", "train_id");
        for (Object object : query.list()) {
            CoachEntity coach = (CoachEntity) object;
            System.out.format("%-10s %-18s %-12s %-12s %s\n", coach.getIdCoach(), coach.getNumberPlace(), coach.getTypeCoach(), coach.getNumberCoach(), coach.getTrainByTrain().getIdTrain());
        }
        //endregion

//region read ticket
        query = session.createQuery("from " + "TicketEntity");
        System.out.format("\ntable ticket ------------------------------------------------\n");
        System.out.format("%-10s %-15s %-18s %s\n", "id_ticket", "date_departuere", "place_departuere", "price_ticket");
        for (Object object : query.list()) {
            TicketEntity ticket = (TicketEntity) object;
            System.out.format("%-10s %-15s %-18s %s\n", ticket.getIdTicket(), ticket.getDateDepartuere(), ticket.getPlaceDepartuere(), ticket.getPriceTicket());
        }
        //endregion

//region read train
        query = session.createQuery("from " + "TrainEntity");
        System.out.format("\ntable train ------------------------------------------------\n");
        System.out.format("%s\n", "id_train");
        for (Object object : query.list()) {
            TrainEntity train = (TrainEntity) object;
            System.out.format("%s\n", train.getIdTrain());
        }
        //endregion

    }

    private static void ReadTrainFilter(Session session) {

        Scanner input = new Scanner(System.in);
        System.out.println("\nInput train for coach:  ");
        String TrainIn = input.next();

        TrainEntity trainEntity = (TrainEntity) session.load(TrainEntity.class, TrainIn);
        if (trainEntity != null) {
            System.out.format("\n(%s): %s\n", TrainIn, "number of places in this train");
            for (CoachEntity obj : trainEntity.getCoachByTrain())
                System.out.format("%s\n", "place number #" + obj.getNumberPlace());
        } else System.out.println("invalid number of train");
    }

    private static void ReadCoachByTicket(Session session) {
        Query query = session.createQuery("from " + "CoachEntity");
        System.out.format("\ntable coach ------------------------------------------------\n");
        System.out.format("%-10s %-15s %-15s \n", "id_coach", "number_place", "type_coach");
        for (Object object : query.list()) {
            CoachEntity coaches = (CoachEntity) object;
            System.out.format("%-10s %-12s %-15s->\n", coaches.getIdCoach(), coaches.getNumberPlace(), coaches.getTypeCoach());
            for (TicketEntity tickets : coaches.getTickets()) {
                System.out.format("%s // %s\n", tickets.getDateDepartuere(), tickets.getPlaceDepartuere());
            }
        }
    }

    private static void ReadTrainTable(Session session) {

        Query query = session.createQuery("from " + "TrainEntity");
        System.out.format("\ntable train ------------------------------------------------\n");
        for (Object object : query.list()) {
            TrainEntity city = (TrainEntity) object;
            System.out.format("%s\n", city.getIdTrain());
        }
    }

    private static void insertTrain(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name city: ");
        String newTrain = input.next();

        session.beginTransaction();
        TrainEntity trainEntity = new TrainEntity(newTrain);
        session.save(trainEntity);
        session.getTransaction().commit();

        System.out.println("end insert train");
    }

    private static void updateCoach(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput number of coach what you want to update: ");
        String numberCoach = input.next();
        System.out.println("Input new number of coach: ");
        String newNumberCoach = input.next();

        CoachEntity coachEntity = (CoachEntity) session.load(CoachEntity.class, numberCoach);
        if (coachEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("update CoachEntity set numberCoach = :code1  where numberCoach = :code2");
            query.setParameter("code1", newNumberCoach);
            query.setParameter("code2", numberCoach);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("end update coaches: " + result);
        } else System.out.println("There is no the coach");
    }

    private static void AddTicketForCoach(Session session) {
        System.out.println("Give a ticket for coach------------------------------------------------");
        Scanner input = new Scanner(System.in);
        System.out.println("Choose ticket date:");
        String numberPlace = input.next();
        System.out.println("Choose coach number of place:");
        String dateDepartuere = input.next();

        Query query = session.createQuery("from " + "CoachEntity where numberPlace = :code");
        query.setParameter("code", numberPlace);

        if (!query.list().isEmpty()) {
            CoachEntity coachEntity = (CoachEntity) query.list().get(0);
            query = session.createQuery("from " + "TicketEntity where dateDepartuere = :code");
            query.setParameter("code", dateDepartuere);
            if (!query.list().isEmpty()) {
                TicketEntity ticketEntity = (TicketEntity) query.list().get(0);
                session.beginTransaction();
                coachEntity.addTicketEntity(ticketEntity);
                session.save(coachEntity);
                session.getTransaction().commit();
                System.out.println("end insert ticket for coach");
            } else {
                System.out.println("There is no the ticket");
            }
        } else {
            System.out.println("There is no this coach");
        }

    }

    private static void AddPairTicketCoachWithProcedure(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput number of place: ");
        String numberPlace = input.next();
        String typeCoach = input.next();
        System.out.println("Input number of coach: ");
        String numberCoach = input.next();
        System.out.println("Input train: ");
        String train = input.next();

        //from JPA 2.1
        StoredProcedureQuery query = session
                .createStoredProcedureQuery("InsertCoach")
                .registerStoredProcedureParameter("number_place_in", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("type_coach_in", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("number_coach_in", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("train_in", String.class, ParameterMode.IN)
                .setParameter("number_place_in", numberPlace)
                .setParameter("type_coach_in", typeCoach)
                .setParameter("number_coach_in", numberCoach)
                .setParameter("train_in", train);
        query.execute();
        String str = (String) query.getResultList().get(0);
        System.out.println(str);

        if (str.equals("OK")) {
            Query query2 = session.createQuery("from " + "CoachEntity");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
            query2 = session.createQuery("from " + "TrainEntity");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
        }
    }

}