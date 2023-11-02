package model;

import java.util.Objects;

public class RequestData {
        private String firstName;
        private String lastName;
        private String dob;

        public RequestData() {

        }

        public RequestData(String firstName, String lastName, String dob) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.dob = dob;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getDob() {
                return dob;
        }

        public void setDob(String dob) {
                this.dob = dob;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                RequestData that = (RequestData) o;
                return getFirstName().equals(that.getFirstName()) && getLastName().equals(that.getLastName()) && getDob().equals(that.getDob());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getFirstName(), getLastName(), getDob());
        }

        @Override
        public String toString() {
                return "RequestData{" +
                        "firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", dob='" + dob + '\'' +
                        '}';
        }
}
