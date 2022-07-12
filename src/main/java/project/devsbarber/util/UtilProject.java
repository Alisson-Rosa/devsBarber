package project.devsbarber.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UtilProject {

    public static List<Long> countPaginasList(long count){
        return countPaginasList(count, 20);
    }

    public static List<Long> countPaginasList(long count, long limitPerPage) {

        List<Long> countPaginaList = new ArrayList<>();
        for(long i=1; count > 0; i++){
            countPaginaList.add(i);
            count -= limitPerPage;
        }

        return countPaginaList;
    }

}
