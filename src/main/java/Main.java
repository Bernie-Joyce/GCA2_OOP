void main() throws Exception {
    CatService service = getCatService();

    List<Cat> cats = service.listCats();
    Cat cat = cats.getFirst();

    String json = service.catToJSON(cat);
    IO.println(json);

    Cat cat1 = service.catFromJSON(json);

    IO.println(cat1.getName());

    String jsonList = service.catListToJSON(cats);

    IO.println(jsonList);


}

private static CatService getCatService() {
    String OS = System.getProperty("os.name").toLowerCase();
    String url;
    if (OS.contains("mac")) {
        url = "jdbc:mysql://localhost:8889/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    } else if (OS.contains("win")) {
        url = "jdbc:mysql://localhost:3306/CatnOwner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    } else {
        throw new RuntimeException("Unsupported OS");
    }
    String user = "root";
    String pass = "root";

    CatDao dao = new JdbcCatDao(url, user, pass);
    return new CatService(dao);
}