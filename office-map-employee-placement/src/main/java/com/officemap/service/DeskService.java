package com.officemap.service;

import com.officemap.entity.Desk;
import com.officemap.repository.DeskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeskService {
    private final DeskRepository deskRepository;

    public DeskService(DeskRepository deskRepository) {
        this.deskRepository = deskRepository;
    }

    public List<Desk> getAllDesksToList() {
        return deskRepository.findAll().stream().toList();
    }

    public Desk createNewDesk(Desk deskToCreate) {
        Desk newDesk = new Desk();
        newDesk.setDeskName(deskToCreate.getDeskName());
        deskRepository.save(newDesk);
        return newDesk;
    }

    public boolean deleteDeskById(Long deskId) {
        deskRepository.deleteById(deskId);
        return true;
    }
}
