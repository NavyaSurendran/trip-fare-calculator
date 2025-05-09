# Trip Fare Calculator
This application processes public transport tap-on/tap-off data (from a CSV file) to calculate trip fares based on specific business rules.

1. Reads a taps.csv file.
2. Parses the CSV into Tap objects.
3. Groups tap-on and tap-off events to form trips.
4. Applies a strategy (Completed, Cancelled, Incomplete) to determine trip status.
5. Calculates fares using FareCalculator.
6. Outputs a summary report including:
       Number of each trip type
7. Outputs the results in trips.csv which will be in root folder.

# Input File Format

```
ID,DateTimeUTC,TapType,StopId,CompanyId,BusID,PAN
1,22-01-2023 13:00:00,ON,Stop1,Company1,Bus37,5500005555555559
2,22-01-2023 13:05:00,OFF,Stop2,Company1,Bus37,5500005555555559
3,22-01-2023 09:20:00,ON,Stop3,Company1,Bus36,4111111111111111
4,23-01-2023 08:00:00,ON,Stop1,Company1,Bus37,4111111111111111 
```
# Output file Format

```
Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status
22-01-2023 13:00:00,22-01-2023 13:05:00,300,Stop1,Stop2,$3.25,Company1,Bus37,5500005555555559,COMPLETED
23-01-2023 08:00:00,23-01-2023 08:00:00,0,Stop1,Stop1,$0.00,Company1,Bus37,4111111111111111,CANCELLED
22-01-2023 09:20:00,,0,Stop3,,$7.30,Company1,Bus36,4111111111111111,INCOMPLETE

```
# Run the Application
Once configured, from your terminal in the project root, run:
   ./gradlew run


Trip Construction Logic:
Trips are constructed by processing a chronologically sorted list of tap events using the following strategy:

1. Pairing ON/OFF Taps:
   Each ON tap is stored in a temporary map using a key composed of busID and PAN.
   When an OFF tap is encountered with a matching key, the corresponding ON tap is retrieved and removed from the map.

2. Determining Trip Type:
   If the ON and OFF tap occur at the same stop, the trip is marked as Cancelled using CancelledTripStrategy.
   Otherwise, it is marked as Completed using CompletedTripStrategy.
   The appropriate TripStatusStrategy is used to create the Trip object.

3. Incomplete Trips:
   Any ON taps remaining in the map after processing all events are considered Incomplete Trips (i.e., no matching OFF tap).
   The maximum fare from the origin stop is charged for such incomplete trips.


