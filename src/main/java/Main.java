void main() throws Exception {

    String url = "jdbc:mysql://localhost:8889/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String user = "root";
    String pass = "root";

    CatDao dao = new JdbcCatDao(url, user, pass);
    CatService service = new CatService(dao);

    for(Cat cat : service.filterAllFemale()){
        IO.println(" - " + cat);
    }

//    IO.println("All cats:");
//    for (Cat cat : service.list())
//        IO.println(" - " + cat);


}
