import React from 'react';
import { useNavigate } from 'react-router-dom';
import CustomersLayout from "@features/customers/components/CustomersLayout.tsx";
import {CustomerNew} from "@features/customers";

const CustomerNewPage: React.FC = () => {
  const navigate = useNavigate();

  const handleCancel = () => {
    navigate('/customers');
  };

  const handleSuccess = () => {
    navigate('/customers');
  };

  return (
    <CustomersLayout>
      <CustomerNew 
        onCancel={handleCancel}
        onSuccess={handleSuccess}
      />
    </CustomersLayout>
  );
};

export default CustomerNewPage;