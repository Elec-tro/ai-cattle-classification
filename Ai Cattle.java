import java.sql.*;

public class CattleClassificationDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cattle_db"; // Database
        String user = "root";
        String password = "yourpassword";

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to Database!");

            // Create table if not exists
            String createTable = """
                CREATE TABLE IF NOT EXISTS cattle_results (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    cattle_id VARCHAR(50),
                    breed VARCHAR(50),
                    confidence DECIMAL(5,2),
                    classification_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;
            con.createStatement().executeUpdate(createTable);

            // Simulated classification result (you can replace with actual model output)
            String cattleId = "CATTLE_101";
            String breed = "Holstein Friesian";
            double confidence = 96.75;

            // Insert record
            String insert = "INSERT INTO cattle_results (cattle_id, breed, confidence) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setString(1, cattleId);
            ps.setString(2, breed);
            ps.setDouble(3, confidence);
            ps.executeUpdate();
            System.out.println("✅ Classification result stored successfully!");

            // Retrieve and display results
            String query = "SELECT * FROM cattle_results";
            ResultSet rs = con.createStatement().executeQuery(query);
            System.out.println("\n📋 Cattle Classification Records:");
            while (rs.next()) {
                System.out.printf("ID: %d | Cattle ID: %s | Breed: %s | Confidence: %.2f%% | Time: %s%n",
                        rs.getInt("id"),
                        rs.getString("cattle_id"),
                        rs.getString("breed"),
                        rs.getDouble("confidence"),
                        rs.getTimestamp("classification_time"));
            }

            // Close connection
            con.close();
            System.out.println("\n🔒 Database connection closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
