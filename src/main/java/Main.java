void main() throws Exception {

    String url = "jdbc:mysql://localhost:8889/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String user = "root";
    String pass = "root";

    CatDao dao = new JdbcCatDao(url, user, pass);
    CatService service = new CatService(dao);


    System.out.println("All cats:");
    for (Cat cat : service.list())
        System.out.println(" - " + cat);
}
