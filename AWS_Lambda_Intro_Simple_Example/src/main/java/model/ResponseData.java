package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class ResponseData {

        private String creationTimeStamp;
        private String fullName;
        private String dob;

        public ResponseData(String fullName, String dob) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                Timestamp timestampNow = new Timestamp(System.currentTimeMillis());

                this.fullName = fullName;
                this.dob = dob;
                this.creationTimeStamp = timestampNow.toString();
        }

        public String getCreationTimeStamp() {
                return creationTimeStamp;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
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
                ResponseData that = (ResponseData) o;
                return getCreationTimeStamp().equals(that.getCreationTimeStamp()) && getFullName().equals(that.getFullName()) && getDob().equals(that.getDob());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getCreationTimeStamp(), getFullName(), getDob());
        }

        @Override
        public String toString() {
                return "ResponseData{" +
                        "creationTimeStamp='" + creationTimeStamp + '\'' +
                        ", fullName='" + fullName + '\'' +
                        ", dob='" + dob + '\'' +
                        '}';
        }

}
