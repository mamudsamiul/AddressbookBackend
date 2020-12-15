package com.capgemini.addressbook.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.addressbook.dto.AddressBookDTO;
import com.capgemini.addressbook.exception.AddressBookException;
import com.capgemini.addressbook.model.AddressBook;
import com.capgemini.addressbook.repository.AddressBookRepository;

@Service
public class AddressBookService implements IAddressBookService{
	
	@Autowired
	private AddressBookRepository addressBookRepository;

	@Override
	public List<AddressBookDTO> getAllContacts() {
		return (List<AddressBookDTO>) addressBookRepository.findAll().stream().map(contact -> new AddressBookDTO(contact)).collect(Collectors.toList());
	}

	@Override
	public AddressBookDTO getContactById(Long id) {
		return addressBookRepository.findById(id).map(contact -> {
			return new AddressBookDTO(contact);
		}).orElseThrow(() -> new AddressBookException("Contact not found"));
	}

	@Override
	public AddressBookDTO createContact(AddressBookDTO addressBookDTO) {
		AddressBook addressBook = new AddressBook(addressBookDTO.getFirstName(), addressBookDTO.getLastNmae(),
				  addressBookDTO.getAddress(), addressBookDTO.getCity(), addressBookDTO.getState(),
				  addressBookDTO.getZip(), addressBookDTO.getPhoneNo(), addressBookDTO.getEmail());
		return new AddressBookDTO(addressBookRepository.save(addressBook));
	}

	@Override
	public AddressBookDTO updateContact(AddressBookDTO addressBookDTO) {
		return addressBookRepository.findById(addressBookDTO.getId()).map(contact -> {
		
		contact.setFirstName(addressBookDTO.getFirstName());
		contact.setLastNmae(addressBookDTO.getLastNmae());
		contact.setAddress(addressBookDTO.getAddress());
		contact.setCity(addressBookDTO.getCity());
		contact.setState(addressBookDTO.getState());
		contact.setZip(addressBookDTO.getZip());
		contact.setPhoneNo(addressBookDTO.getPhoneNo());
		contact.setEmail(addressBookDTO.getEmail()); 
		
		return new AddressBookDTO(addressBookRepository.save(contact));
		}).orElseThrow(() -> new AddressBookException("Contact not found"));
	}

	@Override
	public AddressBookDTO deleteContact(Long id) {
		return addressBookRepository.findById(id)
				.map(contact -> {addressBookRepository.deleteById(contact.getId());
				return new AddressBookDTO(contact);
		}).orElseThrow(() -> new AddressBookException("Id not present"));
	}

}
