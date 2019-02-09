package com.vn.vsii.Service.Impl;

import com.vn.vsii.Service.TransactionService;
import com.vn.vsii.model.Transaction;
import com.vn.vsii.reponsitory.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository billRepository;


    @Override
    public Page<Transaction> findAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Override
    public Transaction findById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Transaction bill) {
    billRepository.save(bill);
    }

    @Override
    public void remove(Long id) {
    billRepository.deleteById(id);
    }

    @Override
    public Transaction findByTransId(long id) {
        return billRepository.findByTransId(id);
    }
}
