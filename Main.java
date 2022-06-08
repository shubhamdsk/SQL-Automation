import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Dimension;

public class Main extends JFrame implements ActionListener {
    JButton createTable, viewTable, dropTable, UpdateTableData, Relationships, Truncate, SQL, Menu, name, done;
    CreateTable cobj;
    RelationshipHandler robj;
    DatabaseConfig db;
    UpdateTable uobj;
    ViewTableInfo vobj;
    login lobj;
    DropTableData dobj;
    SQL_Executer seobj;

    public void callLogin() {
        lobj = new login();
    }

    void createMain() {

        setTitle("SQL Automation");
        setSize(1100, 700);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.darkGray);

        // For Placing Frame in the Middle
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel head = new JLabel("Let's Make It Easy !");
        add(head);
        head.setBounds(610, 80, 460, 60);
        head.setForeground(Color.WHITE);
        head.setFont(new Font("serif", Font.BOLD, 25));

        name = new JButton("SQL AUTOMATION");
        add(name);
        name.setBounds(410, 20, 650, 60);
        name.setBackground(Color.BLACK);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("serif", Font.BOLD, 25));

        Menu = new JButton("Menu");
        add(Menu);
        Menu.setBounds(10, 20, 400, 60);
        Menu.setBackground(Color.BLACK);
        Menu.setForeground(Color.WHITE);
        Menu.setFont(new Font("serif", Font.BOLD, 25));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(460, 360, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(530, 140, 420, 420);
        add(l3);

        SQL = new JButton("SQL WorkSpace");
        add(SQL);
        SQL.setBounds(10, 80, 400, 60);
        SQL.setBackground(Color.BLACK);
        SQL.setForeground(Color.WHITE);
        SQL.setFont(new Font("serif", Font.BOLD, 20));

        createTable = new JButton("Create Table");
        add(createTable);
        createTable.setBounds(10, 140, 400, 60);
        createTable.setBackground(Color.BLACK);
        createTable.setForeground(Color.WHITE);
        createTable.setFont(new Font("serif", Font.BOLD, 20));

        Relationships = new JButton("Relationships");
        add(Relationships);
        Relationships.setBounds(10, 200, 400, 60);
        Relationships.setBackground(Color.BLACK);
        Relationships.setForeground(Color.WHITE);
        Relationships.setFont(new Font("serif", Font.BOLD, 20));

        viewTable = new JButton("View Table ");
        add(viewTable);
        viewTable.setBounds(10, 260, 400, 60);
        viewTable.setBackground(Color.BLACK);
        viewTable.setForeground(Color.WHITE);
        viewTable.setFont(new Font("serif", Font.BOLD, 20));

        UpdateTableData = new JButton("Update Table ");
        add(UpdateTableData);
        UpdateTableData.setBounds(10, 320, 400, 60);
        UpdateTableData.setBackground(Color.BLACK);
        UpdateTableData.setForeground(Color.WHITE);
        UpdateTableData.setFont(new Font("serif", Font.BOLD, 20));

        dropTable = new JButton("Drop Table");
        add(dropTable);
        dropTable.setBounds(10, 380, 400, 60);
        dropTable.setBackground(Color.BLACK);
        dropTable.setForeground(Color.WHITE);
        dropTable.setFont(new Font("serif", Font.BOLD, 20));

        Truncate = new JButton("Truncate Table");
        add(Truncate);
        Truncate.setBounds(10, 440, 400, 60);
        Truncate.setBackground(Color.BLACK);
        Truncate.setForeground(Color.WHITE);
        Truncate.setFont(new Font("serif", Font.BOLD, 20));

        done = new JButton("Done");
        add(done);
        done.setBounds(10, 500, 400, 60);
        done.setBackground(Color.BLACK);
        done.setForeground(Color.WHITE);
        done.setFont(new Font("serif", Font.BOLD, 20));
        // closing frame
        done.addActionListener(e -> {
            dispose();
        });

        // adding action listeners
        SQL.addActionListener(this);
        createTable.addActionListener(this);
        Relationships.addActionListener(this);
        viewTable.addActionListener(this);
        UpdateTableData.addActionListener(this);
        dropTable.addActionListener(this);
        Truncate.addActionListener(this);

    }// MainCopy()

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == createTable) {
            cobj = new CreateTable();
            cobj.createTableMethod();
        }

        if (ae.getSource() == SQL) {
            seobj = new SQL_Executer();
        }
        if (ae.getSource() == viewTable) {
            vobj = new ViewTableInfo();
        }

        if (ae.getSource() == dropTable) {

            dobj = new DropTableData();

        }

        if (ae.getSource() == Truncate) {
            try {
                db = new DatabaseConfig();
                String tname = JOptionPane.showInputDialog(null, "Enter Table Name");
                String truncateSQL = "truncate table " + tname;

                db.ps = db.con.prepareStatement(truncateSQL);
                db.ps.execute();
                JOptionPane.showMessageDialog(null, "Table Truncated Sucessfully", "Sucess",
                        JOptionPane.WARNING_MESSAGE);

            } catch (Exception e) {
            }

        }

        if (ae.getSource() == UpdateTableData) {
            uobj = new UpdateTable();

        }

        if (ae.getSource() == Relationships) {
            robj = new RelationshipHandler();
        }

    }

    public static void main(String[] args) {
        Main m1 = new Main();
        m1.callLogin();
    }
}// class TableFrame

// creating tables in database
// working on validation
class CreateTable extends JFrame implements ActionListener {
    JComboBox<Object> Datatype;
    JTextField fieldname, dsize;
    JRadioButton r1, r2;
    JButton add_field, view_all, search_table, truncate, done;
    static JTable table;
    String datatype[] = { "char", "int", "varchar", "bigint", "text", "numeric" };

    DatabaseConfig db;
    String tname, one_tname;
    ButtonGroup bg;
    int col_cnt = 1;
    int pk_cnt = 1, t_cnt = 1;

    public void createTableMethod() {
        setTitle("Table");
        setSize(1100, 700);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        // For Placing Frame in the Middle
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        getContentPane().setBackground(Color.gray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tname = JOptionPane.showInputDialog(null, "Enter Table Name");

        JLabel table = new JLabel("Create Table " + tname + "");
        add(table);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("serif", Font.BOLD, 35));
        table.setBounds(300, 10, 400, 100);

        JLabel name = new JLabel("FieldName");
        add(name);
        name.setBounds(150, 130, 200, 60);
        name.setBackground(Color.BLACK);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("serif", Font.BOLD, 30));

        fieldname = new JTextField(10);
        add(fieldname);
        fieldname.setBounds(400, 130, 200, 60);
        fieldname.setBackground(Color.BLACK);
        fieldname.setForeground(Color.WHITE);
        fieldname.setFont(new Font("serif", Font.BOLD, 25));

        JLabel data = new JLabel("DataType ");
        add(data);
        data.setBounds(150, 230, 200, 60);
        data.setBackground(Color.BLACK);
        data.setForeground(Color.BLACK);
        data.setFont(new Font("serif", Font.BOLD, 30));

        Datatype = new JComboBox<Object>(datatype);
        add(Datatype);
        Datatype.setBounds(400, 230, 200, 60);
        Datatype.setBackground(Color.BLACK);
        Datatype.setForeground(Color.WHITE);
        Datatype.setFont(new Font("serif", Font.BOLD, 25));

        JLabel pkey = new JLabel("Primary Key");
        add(pkey);
        pkey.setBounds(150, 330, 200, 60);
        pkey.setBackground(Color.BLACK);
        pkey.setForeground(Color.BLACK);
        pkey.setFont(new Font("serif", Font.BOLD, 30));

        r1 = new JRadioButton("yes");
        add(r1);
        r1.setBounds(400, 330, 100, 60);
        r1.setActionCommand("yes");
        r1.setBackground(Color.BLACK);
        r1.setForeground(Color.WHITE);
        r1.setFont(new Font("serif", Font.BOLD, 30));

        r2 = new JRadioButton("no");
        add(r2);
        r2.setBounds(510, 330, 100, 60);
        r2.setActionCommand("no");
        r2.setBackground(Color.BLACK);
        r2.setForeground(Color.WHITE);
        r2.setFont(new Font("serif", Font.BOLD, 30));

        bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);

        JLabel size1 = new JLabel("Size");
        add(size1);
        size1.setBounds(150, 430, 100, 60);
        size1.setBackground(Color.BLACK);
        size1.setForeground(Color.BLACK);
        size1.setFont(new Font("serif", Font.BOLD, 30));

        dsize = new JTextField(10);
        add(dsize);
        dsize.setBounds(400, 430, 100, 60);
        dsize.setText("0");
        dsize.setBackground(Color.BLACK);
        dsize.setForeground(Color.WHITE);
        dsize.setFont(new Font("serif", Font.BOLD, 30));

        add_field = new JButton("Add Column");
        add(add_field);
        add_field.setBounds(700, 450, 220, 60);
        add_field.setBackground(Color.BLACK);
        add_field.setForeground(Color.WHITE);
        add_field.setFont(new Font("serif", Font.BOLD, 30));

        done = new JButton("Done");
        add(done);
        done.setBounds(730, 550, 170, 60);
        done.setBackground(Color.BLACK);
        done.setForeground(Color.WHITE);
        done.setFont(new Font("serif", Font.BOLD, 30));
        // closing frame
        done.addActionListener(e -> {
            dispose();
        });

        // action listeners add_field
        add_field.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        db = new DatabaseConfig();

        if (ae.getSource() == add_field) {

            String cname = (fieldname.getText());
            String datatype = (String) (Datatype.getSelectedItem());
            String initSQL = "create table " + tname + "();";
            int sizeOfDatatype = Integer.parseInt(dsize.getText());
            String primary_key = bg.getSelection().getActionCommand();

            try {
                // creating only table structure(empty table)
                if (col_cnt == 1) {
                    db.ps = db.con.prepareStatement(initSQL);
                    db.ps.execute();
                    col_cnt++;
                    t_cnt++;
                }

                // adding column of primary key only one per table
                if (primary_key == "yes" && pk_cnt == 1) {

                    // checking of integer datatypes since it does not required size
                    if (datatype == "int" || datatype == "bigint") {
                        String SQL = "alter table " + tname + " add " + cname + " " + datatype
                                + ";";
                        db.ps = db.con.prepareStatement(SQL);
                        db.ps.execute();

                        // to add primary key to above table
                        String pk = "alter table " + tname + " add primary key(" + cname + ");";
                        db.ps = db.con.prepareStatement(pk);
                        db.ps.execute();
                        pk_cnt++;

                    } // if of int & bigint
                    else {
                        // for other datatypes since it required size
                        String SQL = "alter table " + tname + " add " + cname + " " + datatype + "("
                                + sizeOfDatatype + ")" + ";";
                        db.ps = db.con.prepareStatement(SQL);
                        db.ps.execute();

                        String pk = "alter table " + tname + " add primary key(" + cname + ");";
                        db.ps = db.con.prepareStatement(pk);
                        db.ps.execute();
                        pk_cnt++;

                    }
                } // if of primary key
                else {
                    // adding columns of non primary key
                    // integer datatypes
                    if (datatype == "int" || datatype == "bigint") {
                        String SQL = "alter table " + tname + " add " + cname + " " + datatype
                                + ";";
                        db.ps = db.con.prepareStatement(SQL);
                        db.ps.execute();
                    } else { // non int datatypes
                        String SQL = "alter table " + tname + " add " + cname + " " + datatype + "("
                                + sizeOfDatatype + ");";
                        db.ps = db.con.prepareStatement(SQL);
                        db.ps.execute();

                    }

                }

                JOptionPane.showMessageDialog(null, "Column Added Sucessfully", "Sucess",
                        JOptionPane.INFORMATION_MESSAGE);

                fieldname.setText("");
                dsize.setText("0");
                db.con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }

        } // add_field

    }// actionPerformed

}// CreateTable

// configuration class of database

class DatabaseConfig {

    // required for other operations
    public Connection con;
    public PreparedStatement ps;
    public Statement st;
    public ResultSet rs;
    public DatabaseMetaData dbmd;
    public ResultSetMetaData rsmd;

    public DatabaseConfig(boolean b) {

    }

    public DatabaseConfig() {

        try {
            // load a driver
            Class.forName("org.postgresql.Driver");

            // Establish Connection
            con = DriverManager.getConnection("jdbc:postgresql://localhost/bcs", "postgres", "dsk");

        } // try
        catch (Exception e) {
            System.out.println(e);
        } // catch

    }// DatabaseConfig()

}// class DatabaseConfig

// Handling relationships in database

class RelationshipHandler extends CreateTable {
    JButton updateTName, updateCName, many_to_many, none;
    DatabaseConfig db;
    String t1 = "", t2 = "", t3 = "", primary_key_t1 = "", primary_key_type_t1 = "";
    String pkey_col_t1 = "";
    String primary_key_t2 = "", primary_key_type_t2 = "";
    String pkey_col_t2 = "";

    RelationshipHandler() {
        setTitle("Table");
        setSize(1100, 700);
        setVisible(true);
        setLayout(null);
        setResizable(false);

        // For Placing Frame in the Middle
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        getContentPane().setBackground(Color.gray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        t1 = JOptionPane.showInputDialog(null, "Enter first Table Name");

        JLabel relation = new JLabel("Choose Relationship");
        add(relation);
        relation.setBounds(400, 50, 500, 60);
        relation.setBackground(Color.BLACK);
        relation.setForeground(Color.WHITE);
        relation.setFont(new Font("serif", Font.BOLD, 35));

        updateTName = new JButton("One To One");
        add(updateTName);
        updateTName.setBounds(400, 150, 300, 50);
        updateTName.setBackground(Color.BLACK);
        updateTName.setForeground(Color.WHITE);
        updateTName.setFont(new Font("serif", Font.BOLD, 30));

        updateCName = new JButton("One To Many");
        add(updateCName);
        updateCName.setBounds(400, 250, 300, 50);
        updateCName.setBackground(Color.BLACK);
        updateCName.setForeground(Color.WHITE);
        updateCName.setFont(new Font("serif", Font.BOLD, 30));

        many_to_many = new JButton("Many To Many");
        add(many_to_many);
        many_to_many.setBounds(400, 350, 300, 50);
        many_to_many.setBackground(Color.BLACK);
        many_to_many.setForeground(Color.WHITE);
        many_to_many.setFont(new Font("serif", Font.BOLD, 30));

        none = new JButton("None");
        add(none);
        none.setBounds(400, 450, 300, 50);
        none.setBackground(Color.BLACK);
        none.setForeground(Color.WHITE);
        none.setFont(new Font("serif", Font.BOLD, 30));

        // closing frame for none relationship
        none.addActionListener(e -> {
            dispose();
        });

        updateTName.addActionListener(this);
        updateCName.addActionListener(this);
        many_to_many.addActionListener(this);

    }// RelationshipHandler()

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            db = new DatabaseConfig();

            if (ae.getSource() == updateTName || ae.getSource() == updateCName) {

                t2 = JOptionPane.showInputDialog(null, "Enter Second Table Name");

                // to find primary key of table
                db.dbmd = db.con.getMetaData();
                db.rs = db.dbmd.getTables(null, null, t1, new String[] { "TABLES" });
                db.rs = db.dbmd.getPrimaryKeys(null, null, t1);

                while (db.rs.next()) {
                    primary_key_t1 = db.rs.getString(4);
                    pkey_col_t1 = db.rs.getString("KEY_SEQ");
                }
                int pk_col_id = Integer.parseInt(pkey_col_t1);

                db.st = db.con.createStatement();
                db.rs = db.st.executeQuery("select * from " + t1);

                db.rsmd = db.rs.getMetaData();
                primary_key_type_t1 = db.rsmd.getColumnTypeName(pk_col_id);

                String init_querry = "alter table " + t2 + " add " + primary_key_t1 + " " + primary_key_type_t1 + ";";
                db.ps = db.con.prepareStatement(init_querry);
                db.ps.execute();

                String ref_querry = "alter table " + t2 + " add foreign key(" + primary_key_t1 + ") " + "references "
                        + t1
                        + "("
                        + primary_key_t1 + ");";

                db.ps = db.con.prepareStatement(ref_querry);
                db.ps.execute();

                JOptionPane.showMessageDialog(null, "Relationship Sucess", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                // generate delay to back towards the main frame
                Thread.sleep(2000);
                dispose();

            }

            if (ae.getSource() == many_to_many) {
                t2 = JOptionPane.showInputDialog(null, "Enter Second Table Name");
                t3 = JOptionPane.showInputDialog(null, "Enter Third Table Name");

                String createSQl = "create table " + t3 + "();";
                db.ps = db.con.prepareStatement(createSQl);
                db.ps.execute();
                // to find primary key and datatype of t1 and adding it into table 3

                db.dbmd = db.con.getMetaData();
                db.rs = db.dbmd.getTables(null, null, t1, new String[] { "TABLES" });
                db.rs = db.dbmd.getPrimaryKeys(null, null, t1);

                while (db.rs.next()) {
                    primary_key_t1 = db.rs.getString(4);
                    pkey_col_t1 = db.rs.getString("KEY_SEQ");
                }
                int pk_col_id_t1 = Integer.parseInt(pkey_col_t1);

                db.st = db.con.createStatement();
                db.rs = db.st.executeQuery("select * from " + t1);

                db.rsmd = db.rs.getMetaData();
                primary_key_type_t1 = db.rsmd.getColumnTypeName(pk_col_id_t1);

                String init_querry = "alter table " + t3 + " add " + primary_key_t1 + " " + primary_key_type_t1 + ";";
                db.ps = db.con.prepareStatement(init_querry);
                db.ps.execute();

                String ref_querry = "alter table " + t3 + " add foreign key(" + primary_key_t1 + ") " + "references "
                        + t1
                        + "("
                        + primary_key_t1 + ");";

                db.ps = db.con.prepareStatement(ref_querry);
                db.ps.execute();

                // to find primary key and datatype of t2 and adding it into table 3
                db.dbmd = db.con.getMetaData();
                db.rs = db.dbmd.getTables(null, null, t2, new String[] { "TABLES" });
                db.rs = db.dbmd.getPrimaryKeys(null, null, t2);

                while (db.rs.next()) {
                    primary_key_t2 = db.rs.getString(4);

                    pkey_col_t2 = db.rs.getString("KEY_SEQ");

                }
                int pk_col_id_t2 = Integer.parseInt(pkey_col_t2);

                db.st = db.con.createStatement();
                db.rs = db.st.executeQuery("select * from " + t2);

                db.rsmd = db.rs.getMetaData();
                primary_key_type_t2 = db.rsmd.getColumnTypeName(pk_col_id_t2);

                String init_querry1 = "alter table " + t3 + " add " + primary_key_t2 + " " + primary_key_type_t2 + ";";
                db.ps = db.con.prepareStatement(init_querry1);
                db.ps.execute();

                String ref_querry1 = "alter table " + t3 + " add foreign key(" + primary_key_t2 + ") " + "references "
                        + t2
                        + "("
                        + primary_key_t2 + ");";

                db.ps = db.con.prepareStatement(ref_querry1);
                db.ps.execute();

                int confirm = JOptionPane.showConfirmDialog(null, "Do You wants to add some extra fields", "Check",
                        JOptionPane.INFORMATION_MESSAGE);
                if (confirm == 0) {
                    new AddFields(t3);

                } else if (confirm == 1) {
                    JOptionPane.showMessageDialog(null, "Relationship Sucess", "Sucess",
                            JOptionPane.INFORMATION_MESSAGE);
                    // generate delay to back towards the main frame
                    Thread.sleep(2000);
                    dispose();
                }

            }
        } // try
        catch (Exception e) {
        }
    }

}// class RelationshipHandler

// class AddFields for adding extra fields for 3rd many to many relationship

class AddFields extends JFrame implements ActionListener {
    JComboBox<Object> Datatype;
    JTextField fieldname, dsize;

    JButton add_field, view_all, search_table, truncate, done;
    static JTable table;
    String datatype[] = { "char", "int", "varchar", "bigint", "text", "numeric" };

    DatabaseConfig db;
    String tname, one_tname;
    ButtonGroup bg;
    int col_cnt = 1;
    int pk_cnt = 1, t_cnt = 1;

    AddFields(String tname) {
        setTitle("Table");
        setSize(700, 400);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        // For Placing Frame in the Middle
        getContentPane().setBackground(Color.CYAN); // For Placing Frame in the Middle
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

        getContentPane().setBackground(Color.PINK);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.tname = tname;

        JLabel table = new JLabel("Adding Fields in Table " + tname + "");
        add(table);
        table.setBounds(300, 10, 200, 40);

        JLabel name = new JLabel("FieldName");
        add(name);
        name.setBounds(70, 70, 100, 40);
        name.setBackground(Color.BLACK);
        name.setForeground(Color.BLACK);

        fieldname = new JTextField(10);
        add(fieldname);
        fieldname.setBounds(190, 70, 100, 40);
        fieldname.setBackground(Color.BLACK);
        fieldname.setForeground(Color.WHITE);

        JLabel data = new JLabel("DataType ");
        add(data);
        data.setBounds(70, 140, 100, 40);
        data.setBackground(Color.BLACK);
        data.setForeground(Color.BLACK);

        Datatype = new JComboBox<Object>(datatype);
        add(Datatype);
        Datatype.setBounds(190, 140, 100, 40);
        Datatype.setBackground(Color.BLACK);
        Datatype.setForeground(Color.WHITE);

        JLabel size1 = new JLabel("Size");
        add(size1);
        size1.setBounds(70, 280, 50, 40);
        size1.setBackground(Color.BLACK);
        size1.setForeground(Color.BLACK);

        dsize = new JTextField(10);
        add(dsize);
        dsize.setBounds(190, 280, 50, 40);
        dsize.setText("0");
        dsize.setBackground(Color.BLACK);
        dsize.setForeground(Color.WHITE);

        add_field = new JButton("Add Column");
        add(add_field);
        add_field.setBounds(400, 100, 120, 40);
        add_field.setBackground(Color.BLACK);
        add_field.setForeground(Color.WHITE);

        done = new JButton("Done");
        add(done);
        done.setBounds(400, 180, 120, 40);

        done.setBackground(Color.BLACK);
        done.setForeground(Color.WHITE);
        // closing frame
        done.addActionListener(e -> {
            dispose();
        });

        // action listeners add_field
        add_field.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        db = new DatabaseConfig();

        if (ae.getSource() == add_field) {

            String cname = (fieldname.getText());
            String datatype = (String) (Datatype.getSelectedItem());
            int sizeOfDatatype = Integer.parseInt(dsize.getText());

            try {

                // adding column
                if (datatype == "int" || datatype == "bigint") {
                    String SQL = "alter table " + tname + " add " + cname + " " + datatype
                            + ";";
                    db.ps = db.con.prepareStatement(SQL);
                    db.ps.execute();
                } else { // non int datatypes
                    String SQL = "alter table " + tname + " add " + cname + " " + datatype + "("
                            + sizeOfDatatype + ");";
                    db.ps = db.con.prepareStatement(SQL);
                    db.ps.execute();

                }

                JOptionPane.showMessageDialog(null, "Column Added Sucessfully", "Sucess",
                        JOptionPane.INFORMATION_MESSAGE);

                fieldname.setText("");
                dsize.setText("0");
                db.con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error Occured", "ERROR", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }

        } // add_field
    }
}

// viewing tables of database
// validation and final code done

class ViewTableInfo extends JFrame implements ActionListener {

    JButton viewAllTables, viewTable, none;
    DatabaseConfig db, dbobj;
    JFrame frm;
    static JTable table;
    int flag = 0;

    ViewTableInfo() {
        setTitle("Table");
        setSize(1100, 700);
        setVisible(true);
        setLayout(null);
        setResizable(false);

        // For Placing Frame in the Middle
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        getContentPane().setBackground(Color.gray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel relation = new JLabel("Choose From Following");
        add(relation);
        relation.setBounds(350, 20, 400, 60);
        relation.setFont(new Font("serif", Font.BOLD, 35));
        relation.setForeground(Color.WHITE);

        viewAllTables = new JButton("View All Tables");
        add(viewAllTables);
        viewAllTables.setBounds(400, 160, 250, 50);
        viewAllTables.setBackground(Color.BLACK);
        viewAllTables.setForeground(Color.WHITE);
        viewAllTables.setFont(new Font("serif", Font.BOLD, 30));

        viewTable = new JButton("View Table");
        add(viewTable);
        viewTable.setBounds(400, 300, 250, 50);
        viewTable.setBackground(Color.BLACK);
        viewTable.setForeground(Color.WHITE);
        viewTable.setFont(new Font("serif", Font.BOLD, 30));

        none = new JButton("None");
        add(none);
        none.setBounds(400, 440, 250, 50);
        none.setBackground(Color.BLACK);
        none.setForeground(Color.WHITE);
        none.setFont(new Font("serif", Font.BOLD, 30));

        // closing frame for none relationship
        none.addActionListener(e -> {
            dispose();
        });

        viewAllTables.addActionListener(this);
        viewTable.addActionListener(this);

    }// ViewTableInfo ()

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == viewTable) {
            try {

                db = new DatabaseConfig();
                String tstr[] = { "TABLE" };

                String t1 = JOptionPane.showInputDialog(null, "Enter Table Name");
                int f = 0;

                if (t1 == "null") {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    db.dbmd = db.con.getMetaData();
                    db.rs = db.dbmd.getTables(null, null, null, tstr);

                    while (db.rs.next()) {
                        if (t1.equals(db.rs.getString("TABLE_NAME"))) {
                            f = 1;
                            break;
                        }

                    }

                    if (f == 1) {

                        String ColumnSQL = "select * from " + t1;
                        db.st = db.con.createStatement();
                        db.rs = db.st.executeQuery(ColumnSQL);
                        db.rsmd = db.rs.getMetaData();

                        int cnt = db.rsmd.getColumnCount();
                        String columnNames[] = new String[cnt];
                        String columnData[] = new String[cnt];

                        for (int i = 1, j = 0; i <= cnt; i++, j++) {
                            columnNames[j] = db.rsmd.getColumnName(i);
                        }

                        frm = new JFrame(t1 + " Tables info");
                        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frm.setSize(1100, 700);
                        frm.setLayout(new BorderLayout());

                        Toolkit toolkit = getToolkit();
                        Dimension size = toolkit.getScreenSize();
                        frm.setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

                        frm.getContentPane().setBackground(Color.lightGray);
                        setResizable(false);
                        DefaultTableModel model = new DefaultTableModel();
                        frm.setFont(new Font("serif", Font.BOLD, 15));
                        model.setColumnIdentifiers(columnNames);
                        table = new JTable();
                        table.setFont(new Font("serif", Font.BOLD, 15));

                        table.setModel(model);

                        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                        table.setFillsViewportHeight(true);

                        JScrollPane scroll = new JScrollPane(table);

                        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                        db.st = db.con.createStatement();
                        db.rs = db.st.executeQuery("select * from " + t1);

                        while (db.rs.next()) {

                            for (int i = 1, j = 0; i <= cnt; i++, j++) {
                                columnData[j] = db.rs.getString(i);
                            }
                            model.addRow(columnData);

                        } // while
                        frm.add(scroll);

                        frm.setVisible(true);
                    } else {

                        JOptionPane.showMessageDialog(null, "Table Does not exist", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        if (ae.getSource() == viewAllTables) {
            try {

                db = new DatabaseConfig();

                String tableStr[] = { "TABLE" };
                String columns[] = { "All Tables of Database" };
                getContentPane().setBackground(Color.lightGray);
                String temp = "";

                frm = new JFrame("Tables info");
                frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frm.setSize(1100, 700);
                setResizable(false);
                frm.setLayout(new BorderLayout());
                Toolkit toolkit = getToolkit();
                Dimension size = toolkit.getScreenSize();
                frm.setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
                frm.getContentPane().setBackground(Color.lightGray);
                DefaultTableModel model = new DefaultTableModel();
                frm.setFont(new Font("serif", Font.BOLD, 15));
                model.setColumnIdentifiers(columns);

                table = new JTable();
                table.setFont(new Font("serif", Font.BOLD, 15));
                table.setModel(model);

                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                table.setFillsViewportHeight(true);

                JScrollPane scroll = new JScrollPane(table);

                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                frm.add(scroll);
                db.dbmd = db.con.getMetaData();
                db.rs = db.dbmd.getTables(null, null, null, tableStr);
                int i = 1;
                while (db.rs.next()) {
                    String TableName[] = new String[1];

                    temp = db.rs.getString("TABLE_NAME");
                    temp = " " + i + ":   " + temp;
                    i++;
                    TableName[0] = temp;
                    model.addRow(TableName);
                }

                frm.setVisible(true);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }// actionPerformed

}// ViewTableInfo

// class login for user login
// validation and final code done

class login extends DatabaseConfig implements ActionListener {

    JLabel l1, l2;
    JTextField t1;
    JPasswordField t2;
    JButton btnlogin, btnregister;
    Main mobj;
    int flag = 0;

    login() {
        JFrame f1 = new JFrame();
        f1.setTitle("Login");
        f1.setBackground(Color.white);
        f1.setLayout(null);

        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Username");
        l1.setBounds(40, 30, 250, 50);
        l1.setFont(new Font("serif", Font.BOLD, 25));
        l1.setForeground(Color.BLACK);
        f1.add(l1);

        l2 = new JLabel("Password");
        l2.setBounds(40, 130, 250, 50);
        l2.setFont(new Font("serif", Font.BOLD, 25));
        l2.setForeground(Color.BLACK);
        f1.add(l2);

        t1 = new JTextField();
        t1.setBounds(200, 30, 250, 50);
        t1.setFont(new Font("serif", Font.BOLD, 25));
        f1.add(t1);

        t2 = new JPasswordField();
        t2.setBounds(200, 130, 250, 50);
        t2.setFont(new Font("serif", Font.BOLD, 25));
        f1.add(t2);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("login.jpg"));
        Image i2 = i1.getImage().getScaledInstance(230, 230, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(470, 40, 200, 260);
        f1.add(l3);

        btnlogin = new JButton("Login");
        btnlogin.setBounds(40, 250, 180, 50);
        btnlogin.setFont(new Font("serif", Font.BOLD, 25));
        // b1.addActionListener(this);
        btnlogin.setBackground(Color.CYAN);
        btnlogin.setForeground(Color.BLACK);
        f1.add(btnlogin);

        btnregister = new JButton("Register");
        btnregister.setBounds(260, 250, 190, 50);
        btnregister.setFont(new Font("serif", Font.BOLD, 25));
        btnregister.setBackground(Color.PINK);
        btnregister.setForeground(Color.BLACK);
        f1.add(btnregister);

        btnlogin.addActionListener(this);
        btnregister.addActionListener(this);

        f1.getContentPane().setBackground(Color.LIGHT_GRAY);

        f1.setVisible(true);
        f1.setSize(715, 400);
        f1.setLocation(400, 180);
        f1.setResizable(false);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnlogin) {
            try {

                String username = t1.getText();
                char arr[] = t2.getPassword();
                String password = "";

                for (char ele : arr) {
                    password = password + ele;
                }

                ps = con.prepareStatement("select username,password from login where username=? and password=?");
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();

                while (rs.next()) {
                    flag = 1;

                }

                if (flag == 1) {
                    JOptionPane.showMessageDialog(null, "Login Sucessfully", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                    mobj = new Main();
                    t1.setText("");
                    t2.setText("");
                    mobj.createMain();

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login Details", "Error",
                            JOptionPane.ERROR_MESSAGE);

                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }
        if (ae.getSource() == btnregister) {
            new registration();
        }

    }

}// class login

// class registeration for user registeration
// validation and final code done

class registration extends JFrame implements ActionListener {

    JLabel l1, l2, name, email;
    JTextField user, tname, temail;
    JPasswordField pass;
    JButton bregister, blogin;
    DatabaseConfig db;
    int Eflag = 0, Pflag = 0;

    registration() {

        setTitle("Registration");

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        name = new JLabel("Name");
        name.setBounds(40, 20, 100, 40);
        name.setFont(new Font("serif", Font.BOLD, 20));
        name.setForeground(Color.BLACK);
        add(name);

        tname = new JTextField();
        tname.setBounds(170, 20, 270, 40);
        tname.setFont(new Font("serif", Font.BOLD, 20));
        add(tname);

        email = new JLabel("Email");
        email.setBounds(40, 90, 100, 40);
        email.setFont(new Font("serif", Font.BOLD, 20));
        email.setForeground(Color.BLACK);
        add(email);

        temail = new JTextField();
        temail.setBounds(170, 90, 270, 40);
        temail.setFont(new Font("serif", Font.BOLD, 20));
        add(temail);

        l1 = new JLabel("Username");
        l1.setBounds(40, 160, 100, 40);
        l1.setFont(new Font("serif", Font.BOLD, 20));
        l1.setForeground(Color.BLACK);
        add(l1);

        user = new JTextField();
        user.setBounds(170, 160, 270, 40);
        user.setFont(new Font("serif", Font.BOLD, 20));
        add(user);

        l2 = new JLabel("Password");
        l2.setBounds(40, 230, 100, 40);
        l2.setFont(new Font("serif", Font.BOLD, 20));
        l2.setForeground(Color.BLACK);
        add(l2);

        pass = new JPasswordField();
        pass.setBounds(170, 230, 270, 40);
        pass.setFont(new Font("serif", Font.BOLD, 20));
        add(pass);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("registration.jpg"));
        Image i2 = i1.getImage().getScaledInstance(220, 230, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(450, 50, 250, 250);
        add(l3);

        bregister = new JButton("Register");
        bregister.setBounds(40, 300, 170, 40);
        bregister.setFont(new Font("serif", Font.BOLD, 25));
        bregister.setBackground(Color.CYAN);
        bregister.setForeground(Color.BLACK);
        add(bregister);

        blogin = new JButton("Login");
        blogin.setBounds(260, 300, 130, 40);
        blogin.setFont(new Font("serif", Font.BOLD, 25));
        blogin.setBackground(Color.PINK);
        blogin.setForeground(Color.BLACK);
        add(blogin);

        blogin.addActionListener(this);
        bregister.addActionListener(this);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        setVisible(true);
        setSize(715, 400);
        setLocation(400, 180);
        setResizable(false);

    }// constructor

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == bregister) {
                String name = tname.getText();
                String email = temail.getText();

                String username = user.getText();
                char arr[] = pass.getPassword();
                String password = "";

                for (char ele : arr) {
                    password = password + ele;
                }
                if (email.endsWith(".com") && email.contains("@")) {
                    Eflag = 1;

                } else {
                    JOptionPane.showMessageDialog(null, "Enter Valid Email", "Error",
                            JOptionPane.ERROR_MESSAGE);

                }
                if (password.length() >= 6) {
                    Pflag = 1;

                } else {
                    JOptionPane.showMessageDialog(null, "Password must be greater than 6", "Error",
                            JOptionPane.ERROR_MESSAGE);

                }

                if (Eflag == 1) {
                    if (Pflag == 1) {
                        db = new DatabaseConfig();
                        db.ps = db.con.prepareStatement("insert into login values(?,?,?,?)");
                        db.ps.setString(1, name);
                        db.ps.setString(2, email);
                        db.ps.setString(3, username);
                        db.ps.setString(4, password);
                        int result = db.ps.executeUpdate();

                        if (result == 1) {

                            JOptionPane.showMessageDialog(null, "Succesfully Registered", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            tname.setText("");
                            temail.setText("");
                            user.setText("");
                            pass.setText("");
                        }
                    }
                }

            }
        } catch (Exception e) {
        }

        if (ae.getSource() == blogin) {
            new login();
        }
    }

}// class registration

// dropping tables of database
// validation done with final code
class DropTableData extends JFrame implements ActionListener {

    JButton dropTName, dropCName, none;
    DatabaseConfig db;

    DropTableData() {
        setTitle("Table");
        setSize(700, 400);
        setVisible(true);
        setLayout(null);
        setResizable(false);

        // For Placing Frame in the Middle
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        getContentPane().setBackground(Color.lightGray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel relation = new JLabel("Choose From Following");
        add(relation);
        relation.setBounds(200, 20, 250, 40);
        relation.setForeground(Color.BLACK);
        relation.setFont(new Font("serif", Font.BOLD, 20));

        dropTName = new JButton("Drop Table Name");
        add(dropTName);
        dropTName.setBounds(200, 80, 200, 40);
        dropTName.setBackground(Color.BLACK);
        dropTName.setForeground(Color.WHITE);

        dropCName = new JButton("Drop Column Name");
        add(dropCName);
        dropCName.setBounds(200, 140, 200, 40);
        dropCName.setBackground(Color.BLACK);
        dropCName.setForeground(Color.WHITE);

        none = new JButton("None");
        add(none);
        none.setBounds(200, 200, 200, 40);
        none.setBackground(Color.BLACK);
        none.setForeground(Color.WHITE);

        // closing frame for none relationship
        none.addActionListener(e -> {
            dispose();
        });

        dropTName.addActionListener(this);
        dropCName.addActionListener(this);

    }// UpdateTable ()

    @Override
    public void actionPerformed(ActionEvent ae) {
        db = new DatabaseConfig();
        try {
            if (ae.getSource() == dropTName) {
                db = new DatabaseConfig();
                String tstr[] = { "TABLE" };

                String t1 = JOptionPane.showInputDialog(null, "Enter Table Name");
                int f = 0;

                if (t1 == "null") {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    db.dbmd = db.con.getMetaData();
                    db.rs = db.dbmd.getTables(null, null, null, tstr);

                    while (db.rs.next()) {
                        if (t1.equals(db.rs.getString("TABLE_NAME"))) {
                            f = 1;
                            break;
                        }

                    }
                    if (f == 1) {

                        String upadteSQl = "drop table " + t1;
                        db.ps = db.con.prepareStatement(upadteSQl);
                        db.ps.execute();
                        JOptionPane.showMessageDialog(null, "Table Dropped Successfully", "Sucess",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        JOptionPane.showMessageDialog(null, "Table not found or in RelationShip", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }

            if (ae.getSource() == dropCName) {
                String tableString[] = { "TABLE" };

                String tn = JOptionPane.showInputDialog(null, "Enter Table Name");
                int f1 = 0;

                if (tn == "null") {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    db.dbmd = db.con.getMetaData();
                    db.rs = db.dbmd.getTables(null, null, null, tableString);

                    while (db.rs.next()) {
                        if (tn.equals(db.rs.getString("TABLE_NAME"))) {
                            f1 = 1;
                            break;
                        }

                    }
                    if (f1 == 1) {
                        String c1 = JOptionPane.showInputDialog(null, "Enter Column Name");
                        String upadteSQl = "alter table " + tn + " drop column " + c1;
                        db.ps = db.con.prepareStatement(upadteSQl);
                        db.ps.execute();
                        JOptionPane.showMessageDialog(null, "column Dropped Successfully", "Sucess",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Table not found or in RelationShip", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}

// Upadating tables of database
// validation done with final code

class UpdateTable extends JFrame implements ActionListener {

    JButton updateTName, updateCName, none;
    DatabaseConfig db;

    UpdateTable() {
        setTitle("Table");
        setSize(700, 400);
        setVisible(true);
        setLayout(null);
        setResizable(false);

        // For Placing Frame in the Middle
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        getContentPane().setBackground(Color.lightGray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel relation = new JLabel("Choose From Following");
        add(relation);
        relation.setBounds(200, 20, 200, 40);
        relation.setBackground(Color.BLACK);
        relation.setForeground(Color.WHITE);

        updateTName = new JButton("Update Table Name");
        add(updateTName);
        updateTName.setBounds(200, 80, 200, 40);
        updateTName.setBackground(Color.BLACK);
        updateTName.setForeground(Color.WHITE);

        updateCName = new JButton("Update Column Name");
        add(updateCName);
        updateCName.setBounds(200, 140, 200, 40);
        updateCName.setBackground(Color.BLACK);
        updateCName.setForeground(Color.WHITE);

        none = new JButton("None");
        add(none);
        none.setBounds(200, 200, 200, 40);
        none.setBackground(Color.BLACK);
        none.setForeground(Color.WHITE);

        // closing frame for none relationship
        none.addActionListener(e -> {
            dispose();
        });

        updateTName.addActionListener(this);
        updateCName.addActionListener(this);

    }// UpdateTable ()

    @Override
    public void actionPerformed(ActionEvent ae) {
        db = new DatabaseConfig();

        if (ae.getSource() == updateTName) {
            try {
                String tstr[] = { "TABLE" };
                int f = 0;
                String t1 = JOptionPane.showInputDialog(null, "Enter old Table Name");

                if (t1 == "null" || t1 == "NULL") {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    db.dbmd = db.con.getMetaData();
                    db.rs = db.dbmd.getTables(null, null, null, tstr);

                    while (db.rs.next()) {
                        if (t1.equals(db.rs.getString(4))) {
                            System.out.println(t1);
                            System.out.println(db.rs.getString(4));
                            f = 1;
                            break;
                        }

                    }
                    if (f == 1) {

                        String t2 = JOptionPane.showInputDialog(null, "Enter new Table Name");

                        String upadteSQl = "alter table " + t1 + " rename to " + t2;
                        db.ps = db.con.prepareStatement(upadteSQl);
                        db.ps.execute();
                        JOptionPane.showMessageDialog(null, "Table Renamed Successfully", "Sucess",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        String t2 = JOptionPane.showInputDialog(null, "Enter new Table Name");

                        String upadteSQl = "alter table " + t1 + " rename to " + t2;
                        db.ps = db.con.prepareStatement(upadteSQl);
                        db.ps.execute();
                        JOptionPane.showMessageDialog(null, "Table Renamed Successfully", "Sucess",
                                JOptionPane.INFORMATION_MESSAGE);
                        // JOptionPane.showMessageDialog(null, "Table not found or in RelationShip",
                        // "Error",
                        // JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (ae.getSource() == updateCName) {
            try {
                String t1 = JOptionPane.showInputDialog(null, "Enter Table Name");
                String tstr[] = { "TABLE" };
                int f = 0;

                if (t1 == "null" || t1 == "NULL") {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    db.dbmd = db.con.getMetaData();
                    db.rs = db.dbmd.getTables(null, null, null, tstr);

                    while (db.rs.next()) {
                        if (t1.equals(db.rs.getString(4))) {
                            System.out.println(t1);
                            System.out.println(db.rs.getString(4));
                            f = 1;
                            break;
                        }

                    }
                    if (f == 1) {

                        String c1 = JOptionPane.showInputDialog(null, "Enter old column Name");
                        String c2 = JOptionPane.showInputDialog(null, "Enter new column Name");

                        String upadteColSQl = "alter table " + t1 + " rename column " + c1 + " to " + c2;
                        db.ps = db.con.prepareStatement(upadteColSQl);
                        db.ps.execute();

                        JOptionPane.showMessageDialog(null, "Column Renamed Successfully", "Sucess",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        JOptionPane.showMessageDialog(null, "Table not found or in RelationShip", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }

    }

}

// class SQL Executer for executing querries
// validation done with final code
class SQL_Executer extends JFrame implements ActionListener {
    JLabel exe_query, query;
    JTextField sql_query;
    JButton go;
    DatabaseConfig db;
    static JTable table;
    JFrame frm;

    SQL_Executer() {
        setTitle("Sql Query Execution");
        setLayout(null);

        setVisible(true);
        setSize(1100, 700);

        setResizable(false);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        getContentPane().setBackground(Color.lightGray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        exe_query = new JLabel("Execute Queries");
        exe_query.setBounds(400, 20, 320, 50);
        exe_query.setFont(new Font("serif", Font.BOLD, 40));
        exe_query.setForeground(Color.BLACK);
        add(exe_query);

        query = new JLabel("Type SQL Query Here");
        query.setBounds(100, 100, 370, 80);
        query.setFont(new Font("serif", Font.BOLD, 30));
        query.setForeground(Color.BLACK);
        add(query);

        sql_query = new JTextField(200);
        sql_query.setBounds(100, 200, 870, 300);
        add(sql_query);
        sql_query.setFont(new Font("Arial", Font.BOLD, 25));

        go = new JButton("Go :)");
        go.setBounds(700, 550, 150, 70);
        go.setBackground(Color.BLACK);
        go.setForeground(Color.WHITE);
        go.setFont(new Font("serif", Font.BOLD, 25));
        add(go);

        go.addActionListener(this);
    }// constructor

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == go) {

            String Query = sql_query.getText();
            Query = Query.toLowerCase();

            int flag = 0;
            db = new DatabaseConfig();

            try {

                if (Query.startsWith("create") || Query.startsWith("alter") || Query.startsWith("truncate")) {
                    db.ps = db.con.prepareStatement(Query);
                    db.ps.execute();
                    sql_query.setText("");

                    JOptionPane.showMessageDialog(null, "Querry Executed Successfully", "Sucess",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                if (Query.startsWith("insert") || Query.startsWith("update") || Query.startsWith("delete")) {
                    db.ps = db.con.prepareStatement(Query);
                    flag = db.ps.executeUpdate();
                    sql_query.setText("");

                }

                if (Query.startsWith("select")) {

                    db.st = db.con.createStatement();
                    db.rs = db.st.executeQuery(Query);
                    db.rsmd = db.rs.getMetaData();

                    int cnt = db.rsmd.getColumnCount();
                    String columnNames[] = new String[cnt];
                    String columnData[] = new String[cnt];

                    for (int i = 1, j = 0; i <= cnt; i++, j++) {
                        columnNames[j] = db.rsmd.getColumnName(i);
                    }

                    frm = new JFrame(" Query Result");
                    frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    frm.setSize(1100, 700);

                    frm.setLayout(new BorderLayout());

                    Toolkit toolkit = getToolkit();
                    Dimension size = toolkit.getScreenSize();
                    frm.setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

                    frm.getContentPane().setBackground(Color.lightGray);

                    setResizable(false);

                    DefaultTableModel model = new DefaultTableModel();

                    frm.setFont(new Font("serif", Font.BOLD, 15));

                    model.setColumnIdentifiers(columnNames);

                    table = new JTable();

                    table.setFont(new Font("serif", Font.BOLD, 15));

                    table.setModel(model);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                    table.setFillsViewportHeight(true);

                    JScrollPane scroll = new JScrollPane(table);

                    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                    db.st = db.con.createStatement();
                    db.rs = db.st.executeQuery(Query);

                    while (db.rs.next()) {

                        for (int i = 1, j = 0; i <= cnt; i++, j++) {
                            columnData[j] = db.rs.getString(i);
                        }
                        model.addRow(columnData);

                    } // while
                    frm.add(scroll);

                    frm.setVisible(true);

                }

                if (flag != 0) {

                    JOptionPane.showMessageDialog(null, "Querry Executed Successfully", "Sucess",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

    }

}
