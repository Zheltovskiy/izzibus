package kz.izzi.bus.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @Column(name = "valid")
    private Boolean valid;

    @NotNull
    @Column(name = "ticket_id", nullable = false)
    private String ticketId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Ticket phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean isValid() {
        return valid;
    }

    public Ticket valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        if (ticket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", valid='" + isValid() + "'" +
            ", ticket_id='" + getTicketId() + "'" +
            "}";
    }
}
