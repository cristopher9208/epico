import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Epicoitem from './epicoitem';
import EpicoitemDetail from './epicoitem-detail';
import EpicoitemUpdate from './epicoitem-update';
import EpicoitemDeleteDialog from './epicoitem-delete-dialog';

const EpicoitemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Epicoitem />} />
    <Route path="new" element={<EpicoitemUpdate />} />
    <Route path=":id">
      <Route index element={<EpicoitemDetail />} />
      <Route path="edit" element={<EpicoitemUpdate />} />
      <Route path="delete" element={<EpicoitemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EpicoitemRoutes;
