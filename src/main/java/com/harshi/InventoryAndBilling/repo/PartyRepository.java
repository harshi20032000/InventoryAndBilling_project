package com.harshi.InventoryAndBilling.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshi.InventoryAndBilling.entities.Party;

public interface PartyRepository extends JpaRepository<Party, Long>{

}
