package com.my;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "coach", schema = "lab_5", catalog = "")
public class CoachEntity {
    private int idCoach;
    private int numberPlace;
    private String typeCoach;
    private String numberCoach;
    private TrainEntity trainByTrain;
    private List<TicketEntity> tickets;

    public CoachEntity() {
    }

    public CoachEntity(int p, String t, String train, String c) {
        numberPlace = p;
        typeCoach = t;
        trainByTrain = new TrainEntity(train);
        numberCoach = c;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coach", nullable = false)
    public int getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(int idCoach) {
        this.idCoach = idCoach;
    }

    @Column(name = "number_place", nullable = false)
    public int getNumberPlace() {
        return numberPlace;
    }

    public void setNumberPlace(int numberPlace) {
        this.numberPlace = numberPlace;
    }

    @Column(name = "type_coach", nullable = false, length = 15)
    public String getTypeCoach() {
        return typeCoach;
    }

    public void setTypeCoach(String typeCoach) {
        this.typeCoach = typeCoach;
    }

    @Column(name = "number_coach", nullable = false, length = 4)
    public String getNumberCoach() {
        return numberCoach;
    }

    public void setNumberCoach(String numberCoach) {
        this.numberCoach = numberCoach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoachEntity that = (CoachEntity) o;

        if (idCoach != that.idCoach) return false;
        if (numberPlace != that.numberPlace) return false;
        if (typeCoach != null ? !typeCoach.equals(that.typeCoach) : that.typeCoach != null) return false;
        if (numberCoach != null ? !numberCoach.equals(that.numberCoach) : that.numberCoach != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCoach;
        result = 31 * result + numberPlace;
        result = 31 * result + (typeCoach != null ? typeCoach.hashCode() : 0);
        result = 31 * result + (numberCoach != null ? numberCoach.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id_train", nullable = false)
    public TrainEntity getTrainByTrain() {
        return trainByTrain;
    }

    public void setTrainByTrain(TrainEntity trainByTrain) {
        this.trainByTrain = trainByTrain;
    }

    @ManyToMany(mappedBy = "coaches")
    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void addTicketEntity(TicketEntity ticketEntity) {
        if (!getTickets().contains(ticketEntity)) {
            getTickets().add(ticketEntity);
        }
        if (!ticketEntity.getCoaches().contains(this)) {
            ticketEntity.getCoaches().add(this);
        }
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }
}
