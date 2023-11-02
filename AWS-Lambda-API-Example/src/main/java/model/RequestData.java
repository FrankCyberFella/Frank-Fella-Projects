package model;

public class RequestData {
        private int movieId;

public RequestData() {}

        public RequestData(int movieId) {
                this.movieId = movieId;
        }

        public int getMovieId() {
                return movieId;
        }

        public void setMovieId(int movieId) {
                this.movieId = movieId;
        }

        @Override
        public String toString() {
                return "RequestData{" +
                        "movieId=" + movieId +
                        '}';
        }
}
