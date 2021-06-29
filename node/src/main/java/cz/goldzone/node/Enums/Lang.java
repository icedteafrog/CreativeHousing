package cz.goldzone.node.Enums;

public enum Lang {
    PREFIX("&8[&6&lHousing&8]"),
    CREATING("&aTvorim vas Housing"),
    STARTING("&aStartuji vas Housing"),
    ERROR(" &c&l[!] &cA jeje neco se pokazilo"),
    MAXH("&cBylo dosazeno maximalního poctu online Housingu"),
    DONE("&a&lHotovo!"),
    DONES("&a&lHotovo! &aMuzes se pripojit na svuj Housing!"),
    DELETE("&c&lHotovo! &cHousing byl smazan vyckej max 30s pred vytvorenim dalsiho nez se zmeny aplikuji!"),
    NODELETE("&cNemas vytvoreny zadny housing"),
    DELETING("&cMazu pockej 5s"),
    INFO("&7Pouzij &e/menu &7pro zobrazeni vsech moznosti, &e/vyhodit <hrac> &7pro vyhozeni hrace, ktery dela neplechu nebo &e/vote &7a zahlasuj pro tuto parcelu!");

    private String name;

    Lang(String name) {
        this.name = name;
    }

    public String getMessage() {
        return this.name;
    }
}
