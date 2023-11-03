package com.frankmo.app.datasource.JdbcDao;

import com.frankmo.app.datasource.dao.frankmoAccountDao;
import com.frankmo.app.datasource.dao.frankmoTransferDao;
import com.frankmo.app.datasource.model.frankmoTransfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcfrankmoTransferDao implements frankmoTransferDao {

    private JdbcTemplate      thefrankmoDataBase;
    private frankmoAccountDao thefrankmoAccountDao;

    public JdbcfrankmoTransferDao(JdbcTemplate theInjectedJdbcTemplate, frankmoAccountDao theInjectedfrankmoAccountDao) {
        this.thefrankmoDataBase = theInjectedJdbcTemplate;
        thefrankmoAccountDao    = theInjectedfrankmoAccountDao;
    }

    @Override
    public frankmoTransfer saveTransfer(frankmoTransfer aTransfer) {
        frankmoTransfer newTransfer = new frankmoTransfer(aTransfer);

        String addTransferSql = " insert into transfer "
                              +" (transfer_type_id, transfer_status_id, account_to, account_from, amount) "
                              +" values( ?, ?, ?, ?, ?)"
                              +"  returning transfer_id";


  //      Long newTransferId = thefrankmoDataBase.queryForObject(addTransferSql
       Long newTransferId = thefrankmoDataBase.queryForObject(addTransferSql
                                                            ,Long.class
                                                            ,aTransfer.getTransferType().ordinal()
                                                            ,aTransfer.getTransferStatus().ordinal()
                                                            ,aTransfer.getTofrankmoAccount().getAccount_id()
                                                            ,aTransfer.getFromfrankmoAccount().getAccount_id()
                                                            ,aTransfer.getAmount().doubleValue()
                                                            );
        newTransfer.setTransferId(newTransferId);

        return getATransferById(newTransferId);
    }

    @Override
    public List<frankmoTransfer> getTransfersForUser(int userId) {
        List<frankmoTransfer> allTransfersForUser = new ArrayList<>();

        String getTransfersForUserSql = "select transfer_id "
                                           + " ,transfer_status_id "
                                           + " ,transfer_type_id "
                                           + " ,account_from "
                                           + " ,account_to "
                                           + " ,amount "
                                           + " ,created_at"
                                        + " from transfer "
                                        + " where account_from = (select account_id from account "
                                        + "                        where user_id = ?)";

        SqlRowSet sqlResult = thefrankmoDataBase.queryForRowSet(getTransfersForUserSql, userId);

        while(sqlResult.next()) {
            allTransfersForUser.add(MapRowToObject(sqlResult));
        }
        return allTransfersForUser;
    }

    @Override
    public frankmoTransfer getATransferById(Long transferIdRequested) {

        frankmoTransfer aTransfer = null;

        String getTransfersForUserSql = "select transfer_id "
                + " ,transfer_status_id "
                + " ,transfer_type_id "
                + " ,account_from "
                + " ,account_to "
                + " ,amount "
                + " ,created_at"
                + " from transfer "
                + " where transfer_id = ?";

        SqlRowSet sqlResult = thefrankmoDataBase.queryForRowSet(getTransfersForUserSql, transferIdRequested);

        if(sqlResult.next()) {
            aTransfer = (MapRowToObject(sqlResult));
        }
        return aTransfer;
    }

    frankmoTransfer MapRowToObject(SqlRowSet rowFromSelect) {
        frankmoTransfer newTransfer = new frankmoTransfer();

        newTransfer.setTransferId(rowFromSelect.getLong("transfer_id"));
        newTransfer.setTransferStatus(frankmoTransfer.TRANSFER_STATUS.values()[rowFromSelect.getInt("transfer_status_id")]);
        newTransfer.setTransferType(frankmoTransfer.TRANSFER_TYPE.values()[rowFromSelect.getInt("transfer_type_id")]);

        Long fromAccountId = rowFromSelect.getLong("account_from");
        newTransfer.setFromfrankmoAccount(thefrankmoAccountDao.getAccountForAccountId(fromAccountId));

        Long toAccountId = rowFromSelect.getLong("account_to");
        newTransfer.setTofrankmoAccount(thefrankmoAccountDao.getAccountForAccountId(toAccountId));

        newTransfer.setAmount(rowFromSelect.getBigDecimal("amount"));

        return newTransfer;
    }
}
