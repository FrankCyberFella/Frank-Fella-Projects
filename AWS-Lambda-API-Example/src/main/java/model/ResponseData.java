package model;

public class ResponseData {

        private String creationTimeStamp;
        private Movie aMovie;

        public ResponseData() {}

        public ResponseData(String creationTimeStamp, Movie aMovie) {
                this.creationTimeStamp = creationTimeStamp;
                this.aMovie = aMovie;
        }

        public String getCreationTimeStamp() {
                return creationTimeStamp;
        }

        public void setCreationTimeStamp(String creationTimeStamp) {
                this.creationTimeStamp = creationTimeStamp;
        }

        public Movie getaMovie() {
                return aMovie;
        }

        public void setaMovie(Movie aMovie) {
                this.aMovie = aMovie;
        }

        @Override
        public String toString() {
                return "ResponseData{" +
                        "creationTimeStamp='" + creationTimeStamp + '\'' +
                        ", aMovie=" + aMovie +
                        '}';
        }
}
