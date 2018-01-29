package com.my;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ticket", schema = "lab_5", catalog = "")
public class TicketEntity {
    private int idTicket;
    private String dateDepartuere;
    private String placeDepartuere;
    private String placeArrival;
    private int priceTicket;
    private List<CoachEntity> coaches;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ticket", nullable = false)
    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    @Basic
    @Column(name = "date_departuere", nullable = false)
    public String getDateDepartuere() {
        return dateDepartuere;
    }

    public void setDateDepartuere(String dateDepartuere) {
        this.dateDepartuere = dateDepartuere;
    }

    @Basic
    @Column(name = "place_departuere", nullable = false, length = 25)
    public String getPlaceDepartuere() {
        return placeDepartuere;
    }

    public void setPlaceDepartuere(String placeDepartuere) {
        this.placeDepartuere = placeDepartuere;
    }

    @Basic
    @Column(name = "place_arrival", nullable = false, length = 25)
    public String getPlaceArrival() {
        return placeArrival;
    }

    public void setPlaceArrival(String placeArrival) {
        this.placeArrival = placeArrival;
    }

    @Basic
    @Column(name = "price_ticket", nullable = false)
    public int getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(int priceTicket) {
        this.priceTicket = priceTicket;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        TicketEntity that = (TicketEntity) object;

        if (idTicket != that.idTicket) return false;
        if (placeArrival != that.placeArrival) return false;
        if (dateDepartuere != null ? !dateDepartuere.equals(that.dateDepartuere) : that.dateDepartuere != null)
            return false;
        if (placeDepartuere != null ? !placeDepartuere.equals(that.placeDepartuere) : that.placeDepartuere != null)
            return false;
        if (priceTicket != that.priceTicket) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTicket;
        result = 31 * result + (dateDepartuere != null ? dateDepartuere.hashCode() : 0);
        result = 31 * result + (placeDepartuere != null ? placeDepartuere.hashCode() : 0);
        result = 31 * result + (placeArrival != null ? placeArrival.hashCode() : 0);
        result = 31 * result + priceTicket;
        return result;
    }

    @ManyToMany
    @JoinTable(name = "ticket_coach", catalog = "", schema = "lab_5", joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "id_ticket", nullable = false), inverseJoinColumns = @JoinColumn(name = "coach_id", referencedColumnName = "id_coach", nullable = false))
    public List<CoachEntity> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<CoachEntity> coaches) {
        this.coaches = coaches;
    }
}
