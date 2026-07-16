import React from 'react';
import { BookStatus, ChapterStatus } from '../types';

type Status = BookStatus | ChapterStatus;

const configs: Record<string, { label: string; className: string }> = {
  NOT_STARTED: { label: 'Not Started', className: 'bg-orange-100 text-orange-800' },
  ON_TRACK:    { label: 'On Track',    className: 'bg-purple-100 text-purple-800' },
  AT_RISK:     { label: 'At Risk',     className: 'bg-amber-100 text-amber-800' },
  OVERDUE:     { label: 'Overdue',     className: 'bg-red-100 text-red-800' },
  COMPLETED:   { label: 'Completed',   className: 'bg-teal-100 text-teal-800' },
  IN_PROGRESS: { label: 'In Progress', className: 'bg-blue-100 text-blue-800' },
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
