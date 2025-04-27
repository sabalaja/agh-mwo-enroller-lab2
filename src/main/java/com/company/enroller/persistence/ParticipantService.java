package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("participantService")
public class ParticipantService {

    DatabaseConnector connector;

    public ParticipantService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Participant> getAll(String orderBy, String sortOrder, String filterValue) {
        String sortOrderString = sortOrder.equals("ASC") ? "ASC" : "DESC";
        String orderByString = " ORDER BY " + (orderBy + " " + sortOrderString);
        String filterString =  " WHERE login LIKE '%" + filterValue + "%'";
        String hql = "FROM Participant";

        if (orderBy != null && !orderBy.equals("")) {
            hql += orderByString;
        }

        if (filterValue != null && !filterValue.equals("")) {
            hql += filterString;

        }

        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Participant findByLogin(String login) {
        return connector.getSession().get(Participant.class, login);
    }

    public Participant add(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(participant);
        transaction.commit();
        return participant;
    }

    public void update(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().merge(participant);
        transaction.commit();
    }

    public void delete(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(participant);
        transaction.commit();
    }

}
