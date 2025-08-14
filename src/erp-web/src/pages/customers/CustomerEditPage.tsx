import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import CustomersLayout from "@features/customers/components/CustomersLayout.tsx";
import {CustomerEdit} from "@features/customers";


const CustomerEditPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const customerId = id ? parseInt(id, 10) : undefined;

  const handleCancel = () => {
    if (customerId) {
      navigate(`/customers/${customerId}`);
    } else {
      navigate('/customers');
    }
  };

  const handleSave = () => {
    if (customerId) {
      navigate(`/customers/${customerId}`);
    } else {
      navigate('/customers');
    }
  };

  return (
    <CustomersLayout>
      <CustomerEdit 
        customerId={customerId}
        onCancel={handleCancel}
        onSave={handleSave}
      />
    </CustomersLayout>
  );
};

export default CustomerEditPage;