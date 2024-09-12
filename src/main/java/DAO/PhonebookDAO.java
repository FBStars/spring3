package DAO;

import VO.PhonebookVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhonebookDAO {
    private String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
    private String jdbcUsername = "system";
    private String jdbcPassword = "1234";

    public PhonebookDAO() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 전화번호부 입력
    public void insertPhonebook(PhonebookVO phonebook) throws SQLException {
        String sql = "INSERT INTO phonebook (id, name, hp, memo) VALUES (phonebook_id_seq.NEXTVAL, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phonebook.getName());
            stmt.setString(2, phonebook.getHp());
            stmt.setString(3, phonebook.getMemo());
            stmt.executeUpdate();
        }
    }

    // 전체 출력
    public List<PhonebookVO> selectAllPhonebook() throws SQLException {
        List<PhonebookVO> phonebookList = new ArrayList<>();
        String sql = "SELECT * FROM phonebook";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String hp = rs.getString("hp");
                String memo = rs.getString("memo");
                phonebookList.add(new PhonebookVO(id, name, hp, memo));
            }
        }
        return phonebookList;
    }

    // 선택 출력 (ID로 조회)
    public PhonebookVO selectPhonebookById(int id) throws SQLException {
        PhonebookVO phonebook = null;
        String sql = "SELECT * FROM phonebook WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String hp = rs.getString("hp");
                    String memo = rs.getString("memo");
                    phonebook = new PhonebookVO(id, name, hp, memo);
                }
            }
        }
        return phonebook;
    }

    // 수정
    public void updatePhonebook(PhonebookVO phonebook) throws SQLException {
        String sql = "UPDATE phonebook SET name = ?, hp = ?, memo = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phonebook.getName());
            stmt.setString(2, phonebook.getHp());
            stmt.setString(3, phonebook.getMemo());
            stmt.setInt(4, phonebook.getId());
            stmt.executeUpdate();
        }
    }

    // 삭제
    public void deletePhonebook(int id) throws SQLException {
        String sql = "DELETE FROM phonebook WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // 검색 기능 추가
    public List<PhonebookVO> searchPhonebook(String search) throws SQLException {
        List<PhonebookVO> phonebookList = new ArrayList<>();
        String sql = "SELECT * FROM phonebook WHERE name LIKE ? OR hp LIKE ? OR memo LIKE ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            stmt.setString(3, "%" + search + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String hp = rs.getString("hp");
                    String memo = rs.getString("memo");
                    phonebookList.add(new PhonebookVO(id, name, hp, memo));
                }
            }
        }
        return phonebookList;
    }

    // DAO 테스트용 메인 메서드는 이전처럼 유지
    public static void main(String[] args) {
        try {
            PhonebookDAO dao = new PhonebookDAO();

            // 입력, 조회, 검색, 수정, 삭제 기능 테스트를 메인에서 수행할 수 있습니다.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
