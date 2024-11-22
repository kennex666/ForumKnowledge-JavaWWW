package com.fit.iuh.services;

import com.fit.iuh.entites.BanList;
import com.fit.iuh.enums.BanListState;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BanListService {
    public List<BanList> getAllBanLists();

    public Optional<BanList> getBanListById(int id);

    public BanList saveOrUpdateBanList(BanList banList);

    public void deleteBanList(int id);

    public List<BanList> findByState(BanListState state);

    public List<BanList> searchByReason(String keyword);

    public List<BanList> findByTimeStartBetween(Date startDate, Date endDate);

    public List<BanList> findByTimeEndBefore(Date endDate);

    public List<BanList> findByUserId(int userId);
}
