package com.example.tacocloud.order;

import com.example.tacocloud.tacos.Taco;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.tacocloud.tacos.TacoRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcOperations jdbcOperations;
    private final TacoRepository tacoRepository;

    public JdbcOrderRepository(JdbcOperations jdbcOperations, TacoRepository tacoRepository){
        this.jdbcOperations = jdbcOperations;
        this.tacoRepository = tacoRepository;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf =  new PreparedStatementCreatorFactory(
                        "insert into Taco_Order "
                                + "(delivery_name, delivery_street, delivery_city, "
                                + "delivery_state, delivery_zip, cc_number, "
                                + "cc_expiration, cc_cvv, placed_at) "
                                + "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
                );
        pscf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryCity(),
                                order.getDeliveryState(),
                                order.getDeliveryZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcOperations.update(psc, keyHolder);

        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        int i=0;
        for (Taco taco : tacos) {
            this.tacoRepository.saveTaco(orderId, i++, taco);
        }
        return order;
    }
}


