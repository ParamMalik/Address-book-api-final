package com.address.book.addressbookapi.controller;

import com.address.book.addressbookapi.dto.ContactDTO;
import com.address.book.addressbookapi.externalservice.ExternalAddressBookService;
import com.address.book.addressbookapi.service.AddressBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private ExternalAddressBookService externalAddressBookService;


    // Get List Of All Contacts

    @GetMapping("/search/{isRemote}")
    public List<ContactDTO> getAllAddressBook(@PathVariable(name = "isRemote") String isRemote) throws JsonProcessingException {
        if (isRemote.equals("y")) {
            return List.of(externalAddressBookService.getAddressList());
        } else {
            return addressBookService.getListofAddress();
        }
    }

    // Get List Of Contacts by firstName

    @GetMapping("/search/{firstName}/{isRemote}")
    public List<ContactDTO> getAddressByFirstName(@PathVariable String firstName, @PathVariable(name = "isRemote") String isRemote) throws JsonProcessingException {
        if (isRemote.equals("y")) {
            return List.of(externalAddressBookService.getAddressListByFirstName(firstName));
        } else {
            return addressBookService.findAddressByFirstName(firstName);
        }


    }

    @PostMapping(path = "/save/{isRemote}")
    public ContactDTO saveAddress(@RequestBody ContactDTO contactDTO, @PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            return externalAddressBookService.saveAddress(contactDTO);
        } else {
            return addressBookService.saveAddress(contactDTO);
        }


    }

    @PutMapping(path = "/update/{contactId}/{isRemote}")
    public void updateAddressBook(@PathVariable Long contactId, @PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            externalAddressBookService.deleteAddress(contactId);
        } else {
            addressBookService.deleteContact(contactId);
        }


    }


}
