package com.my;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "train", schema = "lab_5", catalog = "")
public class TrainEntity {
    private int idTrain;
    private String numberTrain;
    private Collection<CoachEntity> coachByTrain;

    public TrainEntity() {
    }

    public TrainEntity(String t) {
        numberTrain = t;
    }

    @Id
    @Column(name = "number_train", nullable = false, length = 4)
    public String getNumberTrain() {
        return numberTrain;
    }

    public void setNumberTrain(String train) {
        this.numberTrain = train;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        TrainEntity that = (TrainEntity) object;

        if (numberTrain != null ? !numberTrain.equals(that.numberTrain) : that.numberTrain != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return numberTrain != null ? numberTrain.hashCode() : 0;
    }

    @OneToMany(mappedBy = "train")
    public Collection<CoachEntity> getCoachByTrain() {
        return coachByTrain;
    }

    public void setCoachByTrain(Collection<CoachEntity> ticketByTrain) {
        this.coachByTrain = ticketByTrain;
    }
}
