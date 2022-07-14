package com.food.order.system.payment.data.access.credithistory.adapter;


import com.food.order.system.payment.application.service.ports.output.repository.CreditHistoryRepository;
import com.food.order.system.payment.data.access.credithistory.mapper.CreditHistoryDataAccessMapper;
import com.food.order.system.payment.data.access.credithistory.repository.CreditHistoryJpaRepository;
import com.food.order.system.payment.service.domain.entity.CreditHistory;
import com.food.order.sysyem.valueobject.CustomerId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    public CreditHistoryRepositoryImpl(CreditHistoryJpaRepository creditHistoryJpaRepository,
                                       CreditHistoryDataAccessMapper creditHistoryDataAccessMapper) {
        this.creditHistoryJpaRepository = creditHistoryJpaRepository;
        this.creditHistoryDataAccessMapper = creditHistoryDataAccessMapper;
    }

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(creditHistoryJpaRepository
                .save(creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory)));
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        return creditHistoryJpaRepository.findByCustomerId(customerId.getValue())
                .map(creditHistoryList ->
                        creditHistoryList.stream()
                                .map(creditHistoryDataAccessMapper::creditHistoryEntityToCreditHistory)
                                .toList());

    }
}