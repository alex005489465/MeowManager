import { configureStore } from '@reduxjs/toolkit';

export const store = configureStore({
  reducer: {
    // Other reducers can be added here in the future
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;