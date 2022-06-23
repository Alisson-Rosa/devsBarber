package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.Cut;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.repository.CutRepository;

import java.util.List;

@Service
public class CutService {

    @Autowired
    private CutRepository cutRepository;

    public List<Cut> findAll() {
        List <Cut> cutList = (List<Cut>) cutRepository.findAll();

        if(!(cutList.size() > 0)){ //TODO retirar quando cortes estiverem salvos no banco
            Cut cut1 = new Cut();
            cut1.setTypeCut(TypeCut.BARBA);
            cut1.setValue(30.00);
            cutList.add(cut1);

            Cut cut2 = new Cut();
            cut2.setTypeCut(TypeCut.CABELO);
            cut2.setValue(50.00);
            cutList.add(cut2);
        }

        return cutList;
    }
}
