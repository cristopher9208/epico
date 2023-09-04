export interface IEpicoitem {
  id?: number;
  name?: string | null;
  category?: string | null;
  costPrice?: number | null;
  unitPrice?: number | null;
  picFilename?: string | null;
}

export const defaultValue: Readonly<IEpicoitem> = {};
