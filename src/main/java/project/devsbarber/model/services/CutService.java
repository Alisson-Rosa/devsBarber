package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.Cut;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.model.repository.CutRepository;

import java.time.LocalTime;
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

    public Cut get(Long cutId) {
        return cutRepository.getById(cutId);
    }

    public Page<Cut> paginationCut(Pageable pageable) {
        return cutRepository.findAllBarbersWhitPagination(pageable);
    }

    public boolean existTypeCut(TypeCut typeCut) {
        Cut cut = cutRepository.findByTypeCut(typeCut);
        return cut != null;
    }

    public Cut saveCutRegister(Cut cutRegister) {
        LocalTime time = cutRegister.getTime();
        LocalTime init = LocalTime.of(0,15);
        int sizeCut = 0;

        for(int i=1; !init.isAfter(time); i++){
            sizeCut ++;
            init = init.plusMinutes(15);
        }

        cutRegister.setSize(sizeCut);
        cutRegister.setEnable(true);

        return saveOrUpdate(cutRegister);
    }

    private Cut saveOrUpdate(Cut cutRegister) {
        return cutRepository.save(cutRegister);
    }

    public void deleteByid(Long id) {
        cutRepository.deleteById(id);
    }
}
