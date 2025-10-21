package com.my_eshop.my_eshop.repository;

import com.my_eshop.my_eshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { //κληρονομει απο την Jpa οτι μεθοδους εχει - το Users ειναι η κλαση μου και την χρισημοποιει μεσα στις μεθοδους που εχει, εκει βαζω εγω οτι θελω να εχω - το Long ειναι το primary key του αντιστοιχου πινακα
}
