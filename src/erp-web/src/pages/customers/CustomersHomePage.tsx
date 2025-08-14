import React from 'react';
import CustomersLayout from "@features/customers/components/CustomersLayout.tsx";
import CustomerIntroduction from "@features/customers/components/CustomerIntroduction.tsx";


const CustomersHomePage: React.FC = () => {
  return (
    <CustomersLayout>
      <CustomerIntroduction />
    </CustomersLayout>
  );
};

export default CustomersHomePage;