package by.kharchenko.cafe.model.entity;

import java.util.Objects;

public final class Administrator extends User {


    private int idAdministrator;
    private double experience;
    private Status status;
    private String information;

    public int getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(int idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Administrator that = (Administrator) o;
        return Double.compare(that.experience, experience) == 0 && status == that.status && Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), experience, status, information);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "experience=" + experience +
                ", status=" + status +
                ", information='" + information + '\'' +
                ", idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", registrationTime=" + registrationTime +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public enum Status {
        ACCEPTED("accepted"),
        WAITING("waiting"),
        DECLINED("declined");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class AdministratorBuilder extends AbstractUserBuilder<Administrator> {


        public AdministratorBuilder(Administrator administrator) {
            super(administrator);
        }

        public AdministratorBuilder() {
        }

        public AdministratorBuilder withExperience(double experience) {
            this.user.experience = experience;
            return this;
        }

        public AdministratorBuilder withStatus(Status status) {
            this.user.status = status;
            return this;
        }

        public AdministratorBuilder withInformation(String information) {
            this.user.information = information;
            return this;
        }
    }
}
