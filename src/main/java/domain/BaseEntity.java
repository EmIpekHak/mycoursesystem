package domain;

public abstract class BaseEntity {
    private Long id;

    public BaseEntity(Long id) throws InvalidValueException {
        setId(id);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) throws InvalidValueException {
        if (id != null && id >= 0) {
            this.id = id;
        } else {
            throw new InvalidValueException("Kurs-ID muss größer gleich 0 sein");
        }
    }
}
