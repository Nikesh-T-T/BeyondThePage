import React from 'react';
import { BookStatus, ChapterStatus } from '../types';

type Status = BookStatus | ChapterStatus;

const configs: Record<string, { label: string; className: string }> = {
  NOT_STARTED: { label: 'Not Started', className: 'bg-surface-variant text-on-surface' },
  ON_TRACK: { label: 'On Track', className: 'bg-surface-variant text-on-surface' },
  AT_RISK: { label: 'At Risk', className: 'bg-secondary-container text-on-secondary-container' },
  OVERDUE: { label: 'Overdue', className: 'bg-error-container text-on-error-container' },
  COMPLETED: { label: 'Completed', className: 'bg-tertiary-fixed text-on-tertiary-fixed-variant' },
  IN_PROGRESS: { label: 'In Progress', className: 'bg-secondary-container text-on-secondary-container' },
};

interface StatusBadgeProps {
  status: Status;
}

const StatusBadge: React.FC<StatusBadgeProps> = ({ status }) => {
  const config = configs[status] ?? { label: status, className: 'bg-surface-variant text-on-surface' };
  return (
    <span className={`px-sm py-xs rounded-full font-label-caps text-[10px] uppercase tracking-wider whitespace-nowrap ${config.className}`}>
      {config.label}
    </span>
  );
};

export default StatusBadge;
