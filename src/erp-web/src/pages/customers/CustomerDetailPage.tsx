import React from 'react';
import { useParams } from 'react-router-dom';
import CustomersLayout from "@features/customers/components/CustomersLayout.tsx";
import CustomerInfo from "@features/customers/components/CustomerInfo.tsx";

const CustomerDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
    const customerId = id ? parseInt(id, 10) : undefined;
    return (
    <CustomersLayout>
      <div>
        {/* 客戶資訊組件 */}
        <CustomerInfo customerId={customerId} />
      </div>
    </CustomersLayout>
  );
};

export default CustomerDetailPage;