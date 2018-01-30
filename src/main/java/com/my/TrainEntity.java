package com.my;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "train", schema = "lab_5abc", catalog = "")
public class TrainEntity {
    private String idTrain;
    private Collection<CoachEntity> coachByTrain;

    public TrainEntity() {
    }

    public TrainEntity(String t) {
        idTrain = t;
    }

    @Id
    @Column(name = "id_train", nullable = false, length = 4)
    public String getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(String idTrain) {
        this.idTrain = idTrain;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        TrainEntity that = (TrainEntity) object;

        if (idTrain != null ? !idTrain.equals(that.idTrain) : that.idTrain != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idTrain != null ? idTrain.hashCode() : 0;
    }

    @OneToMany(mappedBy = "trainByTrain")
    public Collection<CoachEntity> getCoachByTrain() {
        return coachByTrain;
    }

    public void setCoachByTrain(Collection<CoachEntity> coachByTrain) {
        this.coachByTrain = coachByTrain;
    }
}
