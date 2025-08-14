// Customer Gender constants and type
export const CustomerGender = {
  MALE: 'MALE',
  FEMALE: 'FEMALE',
  OTHER: 'OTHER'
} as const;

export type CustomerGender = typeof CustomerGender[keyof typeof CustomerGender];

// Customer Status constants and type
export const CustomerStatus = {
  ACTIVE: 'ACTIVE',
  SUSPENDED: 'SUSPENDED',
  BLACKLIST: 'BLACKLIST'
} as const;

export type CustomerStatus = typeof CustomerStatus[keyof typeof CustomerStatus];

// Main Customer interface
export interface Customer {
  id: number;
  name: string;
  nick?: string;
  gender: CustomerGender;
  birthDate?: string;
  fbAccount?: string;
  lineAccount?: string;
  email?: string;
  phone: string;
  address: string;
  note?: string;
  status: CustomerStatus;
  createdAt: string;
  updatedAt: string;
}

// Request DTOs
export interface CustomerCreateRequest {
  name: string;
  nick?: string;
  gender: CustomerGender;
  birthDate?: string;
  fbAccount?: string;
  lineAccount?: string;
  email?: string;
  phone: string;
  address: string;
  note?: string;
}

export interface CustomerUpdateRequest {
  id: number;
  name: string;
  nick?: string;
  gender: CustomerGender;
  birthDate?: string;
  fbAccount?: string;
  lineAccount?: string;
  email?: string;
  phone: string;
  address: string;
  note?: string;
}

export interface CustomerStatusUpdateRequest {
  id: number;
  status: CustomerStatus;
}

export interface CustomerSearchRequest {
  page?: number;
  size?: number;
  name?: string;
  phone?: string;
  email?: string;
  status?: CustomerStatus;
}

export interface CustomerSearchStatusRequest {
  page?: number;
  size?: number;
  status: CustomerStatus;
}

export interface CustomerPageableRequest {
  page?: number;
  size?: number;
}

export interface CustomerNameRequest {
  name: string;
}

export interface CustomerPhoneRequest {
  phone: string;
}

export interface CustomerEmailRequest {
  email: string;
}

export interface CustomerSearchIdRequest {
  id: number;
}

export interface CustomerBirthDateRangeRequest {
  startDate: string;
  endDate: string;
}

// Response DTOs
export interface PageCustomer {
  totalPages: number;
  totalElements: number;
  size: number;
  content: Customer[];
  number: number;
  sort: SortObject;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  pageable: PageableObject;
  empty: boolean;
}

export interface SortObject {
  empty: boolean;
  unsorted: boolean;
  sorted: boolean;
}

export interface PageableObject {
  offset: number;
  sort: SortObject;
  unpaged: boolean;
  pageSize: number;
  pageNumber: number;
  paged: boolean;
}