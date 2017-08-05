package kz.izzi.bus.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ticket entity.
 */
public class TicketDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 11, max = 11)
    private String phoneNumber;

    private Boolean valid;

    @NotNull
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean isValid() {
        return valid;
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

        TicketDTO ticketDTO = (TicketDTO) o;
        if(ticketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", valid='" + isValid() + "'" +
            ", ticketId='" + getTicketId() + "'" +
            "}";
    }
}
