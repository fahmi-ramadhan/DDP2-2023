package assignments.assignment3.nota.service;

public class AntarService implements LaundryService{
    private boolean isDone;

    @Override
    public String doWork() {
        isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        if (berat > 4) return berat * 500;
        return 2000;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
