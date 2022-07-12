package project.devsbarber.model.enums;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public enum TypeCut {

    BARBA("Barba"),
    CABELO("Cabelo"),
    BIGODE("Bigode"),
    SOBRANCELHA("Sobrancelha"),
    CABELOBARBABIGODE("Cabelo, Barba e Bigode"),
    PACOTECOMPLETO("Pacote Completo");

    private final String typeCutName;
    TypeCut(String typeCutName){this.typeCutName = typeCutName;}
    public String getTypeCutName(){return typeCutName;}

    private static final Map<String, TypeCut> enumToTypeCutName = new HashMap<>();

    static {
        for (TypeCut typeCut : TypeCut.values()) {
            enumToTypeCutName.put(typeCut.getTypeCutName(), typeCut);
        }
    }

    public static TypeCut getTypeCutToTypeCutName(String typeCutName){
        return enumToTypeCutName.get(typeCutName);
    }

    public static TypeCut[] enumTypeCutAll() {
        return values();
    }
}
