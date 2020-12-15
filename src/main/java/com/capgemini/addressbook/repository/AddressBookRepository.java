package com.capgemini.addressbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.addressbook.model.AddressBook;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long>{

}
