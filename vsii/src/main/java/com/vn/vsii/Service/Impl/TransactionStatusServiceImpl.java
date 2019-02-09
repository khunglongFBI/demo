package com.vn.vsii.Service.Impl;

import com.vn.vsii.Service.TransactionStatusService;
import com.vn.vsii.model.TransactionStatus;
import com.vn.vsii.reponsitory.TransactionStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TransactionStatusServiceImpl implements TransactionStatusService {
    @Autowired
    private TransactionStatusRepository billStatusRepository;
    @Override
    public Iterable<TransactionStatus> findAll() {
        return billStatusRepository.findAll();
    }

    @Override
    public TransactionStatus findById(Long id) {
        return billStatusRepository.findById(id).orElse(null);
    }

    @Override
    public void save(TransactionStatus billStatus) {
        billStatusRepository.save(billStatus);
    }

    @Override
    public void remove(Long id) {
        billStatusRepository.deleteById(id);
    }

    @Override
    public TransactionStatus findByName(String name) {
        return billStatusRepository.findByName(name);
    }
}
