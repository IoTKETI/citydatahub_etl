/*
*
* Define endpoints to be used all entire application.
* */

export const FLOW_STATUS = '/flow/status';
export const FLOW_ROOT = '/flow/root';
export const FLOW_ALL = '/flow';
export const FLOW_BY_ID = '/flow/';

export const CONNECTION = '/connections';

export const PROCESSOR = '/processors';

export const PROCESS_GROUP = '/process-groups';

export const SEARCH = '/search';

export const ALL_LEVEL = '/search/all-level';

// all about nifi-registry
export const GET_ALL_BUCKETS = '/buckets';
export const GET_ALL_ITEMS = '/items';

export const GET_HARDWARE_STATUS = '/hardware/status';

export const LINK_TO_NIFI_INSTANCE = window._env_.NIFI_INSTANCE_URL + window._env_.NIFI_INSTANCE_PORT + '/nifi/?processGroupId=';
