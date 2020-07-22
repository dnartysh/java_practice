
public class Parcel {
    private final int MAX_COUNT_CONTAINERS = 12;
    private final int MAX_COUNT_BOXES = 27;

    private int countTrucks = 1;
    private int countContainers = 1;
    private int countBoxes;

    public Parcel(int count) {
        setCountBoxes(count);
        generateContainers();
        generateTrucks();
    }

    private void generateContainers() {
        countContainers = (int) ((double) (countBoxes) / (double) (MAX_COUNT_BOXES));
    }

    private void generateTrucks() {
        countTrucks = (int) ((double) (countContainers) / (double) (MAX_COUNT_CONTAINERS));
    }

    public void printListParcel() {
        int bufferCountTrucks = 1;
        int bufferCountContainers = 1;
        int bufferCountBoxes = 1;

        for (int i = 1; bufferCountTrucks <= countTrucks; i++) {

            System.out.println("Truck " + bufferCountTrucks + ":");
            bufferCountTrucks++;

            for (int j = 1; bufferCountContainers <= countContainers; j++) {
                if (j <= MAX_COUNT_CONTAINERS) {
                    System.out.println("    Container " + bufferCountContainers + ":");
                    bufferCountContainers++;

                    for (int b = 1; bufferCountBoxes <= countBoxes; b++) {
                        if (b <= MAX_COUNT_BOXES) {
                            System.out.println("        Box " + bufferCountBoxes);
                            bufferCountBoxes++;
                        }
                        else {
                            break;
                        }
                    }
                }
                else {
                    break;
                }
            }
        }

        System.out.println("Need:\nTrucks - " + countTrucks + "\nContainers - " + countContainers);

    }

    private void setCountBoxes(int count) {
        countBoxes = count;
    }
}
