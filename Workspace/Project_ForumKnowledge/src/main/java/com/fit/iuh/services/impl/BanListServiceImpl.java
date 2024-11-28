package com.fit.iuh.services.impl;

import com.fit.iuh.entites.BanList;
import com.fit.iuh.enums.BanListState;
import com.fit.iuh.repositories.BanListRepository;
import com.fit.iuh.services.BanListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BanListServiceImpl implements BanListService {
    @Autowired
    private BanListRepository banListRepository;

    @Override
    public List<BanList> getAllBanLists() {
        return banListRepository.findAll();
    }

    @Override
    public Optional<BanList> getBanListById(int id) {
        return banListRepository.findById(id);
    }

    @Override
    public BanList saveOrUpdateBanList(BanList banList) {
        banList.setUpdatedAt(new Date());
        return banListRepository.save(banList);
    }

    @Override
    public void deleteBanList(int id) {
        banListRepository.deleteById(id);
    }

    @Override
    public List<BanList> findByState(BanListState state) {
        return banListRepository.findByState(state);
    }

    @Override
    public List<BanList> searchByReason(String keyword) {
        return banListRepository.findByReasonContaining(keyword);
    }

    @Override
    public List<BanList> findByTimeStartBetween(Date startDate, Date endDate) {
        return banListRepository.findByTimeStartBetween(startDate, endDate);
    }

    @Override
    public List<BanList> findByTimeEndBefore(Date endDate) {
        return banListRepository.findByTimeEndBefore(endDate);
    }

    @Override
    public List<BanList> findByUserId(int userId) {
        return banListRepository.findByUserId(userId);
    }
}
