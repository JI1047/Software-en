package hello.software_engineering.domain.seat;

public enum SeatClass {

    ECONOMY("이코노미", 50000),
    BUSINESS("비즈니스", 100000),
    FIRST("퍼스트", 200000);

    private final String seatGrade;
    private final int price;

    SeatClass(String seatGrade, int price) {
        this.seatGrade = seatGrade;
        this.price = price;

    }
    public String getSeatGrade() {
        return seatGrade;
    }
    public int getPrice() {
        return price;
    }

}
