package vntu.fcsa.gonchar.dao;

import jakarta.validation.Valid;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ProductDAO {
    PreparedStatement preparedStatement;
    static private int PRODUCTS_COUNT = 0;
    static Connection connection;
    static final String URL = "jdbc:postgresql://localhost:5432/e_shop_db";
    static final String USERNAME = "postgres";
    static final String PASSWORD = "eshop";
    Product product;
    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Product.class)
            .buildSessionFactory();


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Product> getProducts(String type) {
        PRODUCTS_COUNT = 0;
        List<Product> products = new ArrayList<>();
        List<Product> milkProducts = new ArrayList<>();
        List<Product> meatProducts = new ArrayList<>();
        List<Product> drinks = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM products");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setWeight(resultSet.getDouble("weight"));
                product.setPrice(resultSet.getDouble("price"));
                product.setType(resultSet.getString("type"));
                products.add(product);
                ++PRODUCTS_COUNT;
                if (product.getType().equals("milks")) {
                    milkProducts.add(product);
                }
                if (product.getType().equals("meats")) {
                    meatProducts.add(product);
                }
                if (product.getType().equals("drinks")) {
                    drinks.add(product);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        products.sort(Comparator.comparing(Product::getId));
        milkProducts.sort(Comparator.comparing(Product::getId));
        meatProducts.sort(Comparator.comparing(Product::getId));
        drinks.sort(Comparator.comparing(Product::getId));
        switch (type) {
            case "milks":
                return milkProducts;
            case "meats":
                return meatProducts;
            case "drinks":
                return drinks;
            case "all":
                return products;
            default:
                return null;
        }
    }

    public Product read(int id) {
        product = null;
        try {
            preparedStatement =
                    connection.prepareStatement("SELECT * FROM products WHERE id=?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            product = new Product();

            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setWeight(resultSet.getDouble("weight"));
            product.setPrice(resultSet.getDouble("price"));
            product.setType(resultSet.getString("type"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

//    public void create(Product product1) {
//        try {
//            preparedStatement = connection.prepareStatement("INSERT INTO products VALUES(?,?,?,?,?)");
//            preparedStatement.setInt(1, ++PRODUCTS_COUNT);
//            preparedStatement.setString(2, product1.getName());
//            preparedStatement.setDouble(3, product1.getWeight());
//            preparedStatement.setDouble(4, product1.getPrice());
//            preparedStatement.setString(5, product1.getType());
//            preparedStatement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    public void create(Product product1) {
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(product1);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    public void update(int id, @Valid Product product2) {
        try {
            preparedStatement
                    = connection.prepareStatement("UPDATE products SET name=?, weight =?, price=?, type=? WHERE id = ?");

            preparedStatement.setString(1, product2.getName());
            preparedStatement.setDouble(2, product2.getWeight());
            preparedStatement.setDouble(3, product2.getPrice());
            preparedStatement.setString(4, product2.getType());

            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            preparedStatement =
                    connection.prepareStatement("DELETE FROM products WHERE id=?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            --PRODUCTS_COUNT;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateToBuy(int id, Product celledProduct) {
        try {
            read(id);
            preparedStatement
                    = connection.prepareStatement("UPDATE products SET weight =? WHERE id = ?");
            preparedStatement.setLong(2, id);
            celledProduct.setName(product.getName());
            celledProduct.setPrice(product.getPrice());
            celledProduct.setType(product.getType());
            preparedStatement.setDouble(1, product.getWeight() - celledProduct.getWeight());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
